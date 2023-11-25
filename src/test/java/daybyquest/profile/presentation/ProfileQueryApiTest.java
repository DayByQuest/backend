package daybyquest.profile.presentation;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.badge.dto.response.MultipleBadgesResponse;
import daybyquest.profile.application.GetPresetBadgeService;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({ProfileQueryApi.class})
public class ProfileQueryApiTest extends ApiTest {

    @MockBean
    private GetPresetBadgeService getPresetBadgeService;

    @Test
    void 사용자가_설정한_뱃지_목록을_불러온다() throws Exception {
        // given
        given(getPresetBadgeService.invoke(ALICE.username))
                .willReturn(new MultipleBadgesResponse(List.of(BADGE_1.응답(1L), BADGE_2.응답(2L))));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/badge/{username}", ALICE.username)
                .header("Authorization", "UserId 1"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("profile/readBadges"));
        then(getPresetBadgeService).should().invoke(ALICE.username);
    }
}
