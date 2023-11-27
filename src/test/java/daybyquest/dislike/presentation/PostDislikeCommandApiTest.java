package daybyquest.dislike.presentation;

import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.dislike.application.DeletePostDislikeService;
import daybyquest.dislike.application.SavePostDislikeService;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({PostDislikeCommandApi.class})
public class PostDislikeCommandApiTest extends ApiTest {

    @MockBean
    private SavePostDislikeService savePostDislikeService;

    @MockBean
    private DeletePostDislikeService deletePostDislikeService;

    @Test
    void 게시물에_관심없음을_누른다() throws Exception {
        // given
        final Long postId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/post/{postId}/dislike", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("dislike/saveDislike"));
        then(savePostDislikeService).should().invoke(로그인_ID, postId);
    }

    @Test
    void 게시물_관심없음을_취소한다() throws Exception {
        // given
        final Long postId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                delete("/post/{postId}/dislike", postId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("dislike/deleteDislike"));
        then(deletePostDislikeService).should().invoke(로그인_ID, postId);
    }
}
