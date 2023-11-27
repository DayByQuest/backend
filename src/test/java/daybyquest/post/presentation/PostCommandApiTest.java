package daybyquest.post.presentation;

import static daybyquest.post.domain.PostState.SUCCESS;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.post.application.CheckPostService;
import daybyquest.post.application.GetPostService;
import daybyquest.post.application.JudgePostService;
import daybyquest.post.application.SavePostService;
import daybyquest.post.application.SwipePostService;
import daybyquest.post.domain.Post;
import daybyquest.post.dto.request.CheckPostRequest;
import daybyquest.post.dto.request.JudgePostRequest;
import daybyquest.post.dto.request.SavePostRequest;
import daybyquest.support.test.ApiTest;
import daybyquest.user.query.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({PostCommandApi.class})
public class PostCommandApiTest extends ApiTest {

    @MockBean
    private SavePostService savePostService;

    @MockBean
    private SwipePostService swipePostService;

    @MockBean
    private GetPostService getPostService;

    @MockBean
    private JudgePostService judgePostService;

    @MockBean
    private CheckPostService checkPostService;

    @Test
    void 게시물을_생성한다() throws Exception {
        // given
        final Long postId = 2L;
        final Profile profile = ALICE.프로필(로그인_ID);
        given(savePostService.invoke(eq(로그인_ID), any(), any())).willReturn(postId);
        given(getPostService.invoke(로그인_ID, postId)).willReturn(POST_1.퀘스트와_함께_응답(postId, profile));

        final SavePostRequest savePostRequest = 게시물_생성_요청(POST_1.생성(로그인_ID, 3L));
        final MockMultipartFile file1 = 사진을_전송한다("images");
        final MockMultipartFile file2 = 사진을_전송한다("images");
        final MockMultipartFile request = 멀티파트_JSON을_전송한다("request", savePostRequest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/post")
                        .file(file1)
                        .file(file2)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/savePost",
                        requestParts(partWithName("images").description("사진 (최대 5개)"),
                                partWithName("request").description("요청")))
                );
        then(savePostService).should().invoke(eq(로그인_ID), any(), any());
    }

    @Test
    void 게시물을_스와이프_한다() throws Exception {
        // given
        final Long postId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/post/{postId}/swipe", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/swipePost"));
        then(swipePostService).should().invoke(로그인_ID, postId);
    }

    @Test
    void 게시물을_판정한다() throws Exception {
        // given
        final Long postId = 2L;
        final JudgePostRequest request = 게시물_판정_요청();

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/post/{postId}/judge", postId)
                        .header(인증_헤더_이름, 어드민_인증_헤더)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/judgePost"));
        then(judgePostService).should().invoke(eq(postId), any());
    }

    @Test
    void 그룹장이_게시물을_판정한다() throws Exception {
        // given
        final Long postId = 2L;
        final CheckPostRequest request = 그룹장의_게시물_판정_요청();

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/group/{postId}/post", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("post/checkGroupPost"));
        then(checkPostService).should().invoke(eq(로그인_ID), eq(postId), any());
    }

    private SavePostRequest 게시물_생성_요청(final Post post) {
        final SavePostRequest request = new SavePostRequest();
        ReflectionTestUtils.setField(request, "content", post.getContent());
        ReflectionTestUtils.setField(request, "questId", post.getQuestId());
        return request;
    }

    private JudgePostRequest 게시물_판정_요청() {
        final JudgePostRequest request = new JudgePostRequest();
        ReflectionTestUtils.setField(request, "judgement", SUCCESS.toString());
        return request;
    }

    private CheckPostRequest 그룹장의_게시물_판정_요청() {
        final CheckPostRequest request = new CheckPostRequest();
        ReflectionTestUtils.setField(request, "approval", true);
        return request;
    }
}
