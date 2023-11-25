package daybyquest.relation.presentation;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.relation.application.GetFollowersService;
import daybyquest.relation.application.GetFollowingsService;
import daybyquest.support.test.ApiTest;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({FollowQueryApi.class})
public class FollowQueryApiTest extends ApiTest {

    @MockBean
    private GetFollowersService getFollowersService;

    @MockBean
    private GetFollowingsService getFollowingsService;

    @Test
    void 팔로잉_목록을_조회한다() throws Exception {
        // given
        given(getFollowingsService.invoke(eq(로그인_ID), any())).willReturn(
                new PageProfilesResponse(List.of(ProfileResponse.of(ALICE.프로필(1L)),
                        ProfileResponse.of(BOB.프로필(2L))), 1L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(get("/followings")
                .param("limit", "5")
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("relation/readFollowings"));
        then(getFollowingsService).should().invoke(eq(로그인_ID), any());
    }

    @Test
    void 팔로워_목록을_조회한다() throws Exception {
        // given
        given(getFollowersService.invoke(eq(로그인_ID), any())).willReturn(
                new PageProfilesResponse(List.of(ProfileResponse.of(ALICE.프로필(1L)),
                        ProfileResponse.of(BOB.프로필(2L))), 1L)
        );

        // when
        final ResultActions resultActions = mockMvc.perform(get("/followers")
                .param("limit", "5")
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("relation/readFollowers"));
        then(getFollowersService).should().invoke(eq(로그인_ID), any());
    }
}
