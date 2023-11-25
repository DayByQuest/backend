package daybyquest.user.presentation;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.support.test.ApiTest;
import daybyquest.user.application.CheckUsernameService;
import daybyquest.user.application.GetMyProfileService;
import daybyquest.user.application.GetProfileByUsernameService;
import daybyquest.user.application.GetVisibilityService;
import daybyquest.user.application.SearchUserService;
import daybyquest.user.dto.response.MyProfileResponse;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({UserQueryApi.class})
public class UserQueryApiTest extends ApiTest {

    @MockBean
    private GetProfileByUsernameService getProfileByUsernameService;

    @MockBean
    private GetMyProfileService getMyProfileService;

    @MockBean
    private CheckUsernameService checkUsernameService;

    @MockBean
    private GetVisibilityService getVisibilityService;

    @MockBean
    private SearchUserService searchUserService;

    @Test
    void 프로필을_조회한다() throws Exception {
        // given
        given(getProfileByUsernameService.invoke(로그인_ID, ALICE.username))
                .willReturn(ProfileResponse.of(ALICE.프로필(2L)));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/profile/{username}", ALICE.username)
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("user/read"));
        then(getProfileByUsernameService).should().invoke(로그인_ID, ALICE.username);
    }

    @Test
    void 내_프로필을_조회한다() throws Exception {
        // given
        given(getMyProfileService.invoke(로그인_ID)).willReturn(MyProfileResponse.of(ALICE.프로필(1L)));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/profile")
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("user/readMyself"));
        then(getMyProfileService).should().invoke(로그인_ID);
    }

    @Test
    void 사용자_이름_중복_조회한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(get("/profile/{username}/check", ALICE.username));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(문서화한다("user/checkUsername"));
        then(checkUsernameService).should().invoke(ALICE.username);
    }

    @Test
    void 계정_공개_범위를_조회한다() throws Exception {
        // given & when
        final ResultActions resultActions = mockMvc.perform(get("/profile/visibility")
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("user/readVisibility"));
        then(getVisibilityService).should().invoke(로그인_ID);
    }

    @Test
    void 사용자를_검색한다() throws Exception {
        // given
        given(searchUserService.invoke(eq(로그인_ID), any(), any())).willReturn(
                new PageProfilesResponse(List.of(ProfileResponse.of(ALICE.프로필(1L)),
                        ProfileResponse.of(BOB.프로필(2L))), 1L)
        );

        // given & when
        final ResultActions resultActions = mockMvc.perform(get("/search/user")
                .param("keyword", "dlwlrma")
                .param("limit", "5")
                .param("lastId", "0")
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("user/search"));
        then(searchUserService).should().invoke(eq(로그인_ID), any(), any());
    }
}
