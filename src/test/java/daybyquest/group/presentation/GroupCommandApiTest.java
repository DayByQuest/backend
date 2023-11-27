package daybyquest.group.presentation;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.group.application.DeleteMemberService;
import daybyquest.group.application.SaveGroupService;
import daybyquest.group.application.SaveMemberService;
import daybyquest.group.domain.Group;
import daybyquest.group.dto.request.SaveGroupRequest;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({GroupCommandApi.class})
public class GroupCommandApiTest extends ApiTest {

    @MockBean
    private SaveGroupService saveGroupService;

    @MockBean
    private SaveMemberService saveMemberService;

    @MockBean
    private DeleteMemberService deleteMemberService;

    @Test
    void 그룹을_생성한다() throws Exception {
        // given
        given(saveGroupService.invoke(any(), any(), any())).willReturn(1L);
        final SaveGroupRequest saveGroupRequest = 그룹_생성_요청(GROUP_1.생성());
        final MockMultipartFile file =
                new MockMultipartFile("image", "image.png",
                        MediaType.MULTIPART_FORM_DATA_VALUE, "file content".getBytes());
        final MockMultipartFile request =
                new MockMultipartFile("request", "request",
                        MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(saveGroupRequest));

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/group")
                        .file(file)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/saveGroup",
                        requestParts(partWithName("image").description("사진 파일"),
                                partWithName("request").description("요청")))
                );
        then(saveGroupService).should().invoke(eq(로그인_ID), any(), any());
    }

    @Test
    void 그룹에_가입한다() throws Exception {
        // given
        final Long groupId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/group/{groupId}/user", groupId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/saveMember"));
        then(saveMemberService).should().invoke(로그인_ID, groupId);
    }

    @Test
    void 그룹에_탈퇴한다() throws Exception {
        // given
        final Long groupId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                delete("/group/{groupId}/user", groupId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("group/deleteMember"));
        then(deleteMemberService).should().invoke(로그인_ID, groupId);
    }

    private SaveGroupRequest 그룹_생성_요청(final Group group) {
        final SaveGroupRequest request = new SaveGroupRequest();
        ReflectionTestUtils.setField(request, "name", group.getName());
        ReflectionTestUtils.setField(request, "description", group.getDescription());
        ReflectionTestUtils.setField(request, "interest", group.getInterest());
        return request;
    }
}
