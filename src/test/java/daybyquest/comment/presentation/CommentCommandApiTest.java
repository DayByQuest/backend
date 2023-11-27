package daybyquest.comment.presentation;

import static daybyquest.support.fixture.CommentFixtures.댓글_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.comment.application.SaveCommentService;
import daybyquest.comment.domain.Comment;
import daybyquest.comment.dto.request.SaveCommentRequest;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({CommentCommandApi.class})
public class CommentCommandApiTest extends ApiTest {

    @MockBean
    private SaveCommentService saveCommentService;

    @Test
    void 댓글을_생성한다() throws Exception {
        // given
        final Long postId = 2L;
        final SaveCommentRequest request = 댓글_생성_요청(댓글_1.생성(postId, 로그인_ID));

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/comment/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("comment/saveComment"));
        then(saveCommentService).should().invoke(eq(로그인_ID), eq(postId), any());
    }

    private SaveCommentRequest 댓글_생성_요청(final Comment comment) {
        final SaveCommentRequest request = new SaveCommentRequest();
        ReflectionTestUtils.setField(request, "content", comment.getContent());
        return request;
    }
}
