package daybyquest.comment.presentation;

import static daybyquest.support.fixture.CommentFixtures.댓글_1;
import static daybyquest.support.fixture.CommentFixtures.댓글_2;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.comment.application.GetCommentsByPostIdService;
import daybyquest.comment.dto.response.PageCommentsResponse;
import daybyquest.support.test.ApiTest;
import daybyquest.user.query.Profile;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({CommentQueryApi.class})
public class CommentQueryApiTest extends ApiTest {

    @MockBean
    private GetCommentsByPostIdService getCommentsByPostIdService;

    @Test
    void 게시물의_댓글_목록을_확인한다() throws Exception {
        // given
        final Long postId = 2L;
        final Profile profile = ALICE.프로필(로그인_ID);
        given(getCommentsByPostIdService.invoke(eq(로그인_ID), eq(postId), any())).willReturn(
                new PageCommentsResponse(List.of(댓글_1.응답(1L, profile), 댓글_2.응답(2L, profile)), 2L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/comment/{postId}", postId)
                        .param("limit", "5")
                        .param("lastId", "0")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("comment/getComments"));
        then(getCommentsByPostIdService).should().invoke(eq(로그인_ID), eq(postId), any());
    }
}
