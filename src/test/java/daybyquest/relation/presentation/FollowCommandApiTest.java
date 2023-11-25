package daybyquest.relation.presentation;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.relation.application.DeleteFollowService;
import daybyquest.relation.application.DeleteFollowerService;
import daybyquest.relation.application.SaveFollowService;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({FollowCommandApi.class})
public class FollowCommandApiTest extends ApiTest {

    @MockBean
    private SaveFollowService saveFollowService;

    @MockBean
    private DeleteFollowService deleteFollowService;

    @MockBean
    private DeleteFollowerService deleteFollowerService;

    @Test
    void 팔로우를_한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(
                post("/profile/{username}/follow", ALICE.username)
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("relation/saveFollow"));
        then(saveFollowService).should().invoke(로그인_ID, ALICE.username);
    }

    @Test
    void 언팔로우를_한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(
                delete("/profile/{username}/follow", ALICE.username)
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("relation/deleteFollow"));
        then(deleteFollowService).should().invoke(로그인_ID, ALICE.username);
    }

    @Test
    void 팔로워를_삭제한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(
                delete("/profile/{username}/follower", ALICE.username)
                        .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("relation/deleteFollower"));
        then(deleteFollowerService).should().invoke(로그인_ID, ALICE.username);
    }
}
