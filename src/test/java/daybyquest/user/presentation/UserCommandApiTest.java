package daybyquest.user.presentation;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.user.domain.UserVisibility.PRIVATE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.support.test.ApiTest;
import daybyquest.user.application.DeleteUserImageService;
import daybyquest.user.application.SaveUserService;
import daybyquest.user.application.UpdateUserImageService;
import daybyquest.user.application.UpdateUserInterestService;
import daybyquest.user.application.UpdateUserService;
import daybyquest.user.application.UpdateVisibilityService;
import daybyquest.user.domain.User;
import daybyquest.user.dto.request.SaveUserRequest;
import daybyquest.user.dto.request.UpdateUserInterestRequest;
import daybyquest.user.dto.request.UpdateUserRequest;
import daybyquest.user.dto.request.UpdateUserVisibilityRequest;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

public class UserCommandApiTest extends ApiTest {

    @MockBean
    private SaveUserService saveUserService;

    @MockBean
    private UpdateUserService updateUserService;

    @MockBean
    private UpdateVisibilityService updateVisibilityService;

    @MockBean
    private UpdateUserInterestService updateUserInterestService;

    @MockBean
    private UpdateUserImageService updateUserImageService;

    @MockBean
    private DeleteUserImageService deleteUserImageService;

    @Test
    void 회원가입을_한다() throws Exception {
        // given
        given(saveUserService.invoke(any())).willReturn(1L);
        final SaveUserRequest request = 회원가입_요청(ALICE.생성());

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()))
                );
        verify(saveUserService).invoke(any(SaveUserRequest.class));
    }

    @Test
    void 프로필을_수정한다() throws Exception {
        // given
        final UpdateUserRequest request = 사용자_수정_요청(ALICE.생성());

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("UserId 헤더")))
                );
        verify(updateUserService).invoke(anyLong(), any(UpdateUserRequest.class));
    }

    @Test
    void 사용자_가시성을_수정한다() throws Exception {
        // given
        final UpdateUserVisibilityRequest request = 사용자_가시성_수정_요청();

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/profile/visibility")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/visibility/update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("UserId 헤더")))
                );
        verify(updateVisibilityService).invoke(anyLong(), any(UpdateUserVisibilityRequest.class));
    }

    @Test
    void 사용자_관심사를_수정한다() throws Exception {
        // given
        final UpdateUserInterestRequest request = 사용자_관심사_수정_요청();

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/profile/interest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/interest/update",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("UserId 헤더")))
                );
        verify(updateUserInterestService).invoke(anyLong(), any(UpdateUserInterestRequest.class));
    }

    @Test
    void 사용자_사진을_수정한다() throws Exception {
        // given
        final MockMultipartFile file =
                new MockMultipartFile("image", "image.png", "multipart/form-data", "file content".getBytes());

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart(HttpMethod.PATCH, "/profile/image")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/image/update",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("UserId 헤더")),
                        requestParts(partWithName("image").description("사진 파일")))
                );
        verify(updateUserImageService).invoke(anyLong(), any(MultipartFile.class));
    }

    @Test
    void 사용자_사진을_삭제한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(
                delete("/profile/image")
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/image/delete",
                        preprocessResponse(prettyPrint()),
                        requestHeaders(headerWithName("Authorization").description("UserId 헤더")))
                );
        verify(deleteUserImageService).invoke(anyLong());
    }

    private SaveUserRequest 회원가입_요청(final User user) {
        final SaveUserRequest request = new SaveUserRequest();
        ReflectionTestUtils.setField(request, "username", user.getUsername());
        ReflectionTestUtils.setField(request, "email", user.getEmail());
        ReflectionTestUtils.setField(request, "name", user.getName());
        return request;
    }

    private UpdateUserRequest 사용자_수정_요청(final User user) {
        final UpdateUserRequest request = new UpdateUserRequest();
        ReflectionTestUtils.setField(request, "username", user.getUsername());
        ReflectionTestUtils.setField(request, "name", user.getName());
        return request;
    }

    private UpdateUserVisibilityRequest 사용자_가시성_수정_요청() {
        final UpdateUserVisibilityRequest request = new UpdateUserVisibilityRequest();
        ReflectionTestUtils.setField(request, "visibility", PRIVATE.toString());
        return request;
    }

    private UpdateUserInterestRequest 사용자_관심사_수정_요청() {
        final UpdateUserInterestRequest request = new UpdateUserInterestRequest();
        ReflectionTestUtils.setField(request, "interests", Set.of("관심사1", "관심사2"));
        return request;
    }
}
