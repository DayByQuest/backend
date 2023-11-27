package daybyquest.like.presentation;

import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.like.application.DeletePostLikeService;
import daybyquest.like.application.SavePostLikeService;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({PostLikeCommandApi.class})
public class PostLikeCommandApiTest extends ApiTest {

    @MockBean
    private SavePostLikeService savePostLikeService;

    @MockBean
    private DeletePostLikeService deletePostLikeService;

    @Test
    void 게시물에_좋아요를_누른다() throws Exception {
        // given
        final Long postId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/post/{postId}/like", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("like/saveLike"));
        then(savePostLikeService).should().invoke(로그인_ID, postId);
    }

    @Test
    void 게시물_좋아요를_취소한다() throws Exception {
        // given
        final Long postId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                delete("/post/{postId}/like", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("like/deleteLike"));
        then(deletePostLikeService).should().invoke(로그인_ID, postId);
    }
}
