package daybyquest.badge.presentation;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.badge.application.GetMyBadgesService;
import daybyquest.badge.dto.response.PageBadgesResponse;
import daybyquest.support.test.ApiTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({BadgeQueryApi.class})
public class BadgeQueryApiTest extends ApiTest {

    @MockBean
    private GetMyBadgesService getMyBadgesService;

    @Test
    void 내_뱃지_목록을_조회한다() throws Exception {
        // given
        given(getMyBadgesService.invoke(eq(로그인_ID), any())).willReturn(
                new PageBadgesResponse(List.of(BADGE_1.응답(1L), BADGE_2.응답(2L)), LocalDateTime.MAX));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/badge")
                        .param("limit", "5")
                        .param("lastTime", "1970-01-01 00:00")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("badge/getMyBadges"));
        then(getMyBadgesService).should().invoke(eq(로그인_ID), any());
    }
}
