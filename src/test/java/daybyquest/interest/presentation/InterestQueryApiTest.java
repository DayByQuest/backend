package daybyquest.interest.presentation;

import static daybyquest.support.fixture.InterestFixtures.INTERST_1;
import static daybyquest.support.fixture.InterestFixtures.INTERST_2;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.interest.application.GetInterestsService;
import daybyquest.interest.dto.response.MultiInterestResponse;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({InterestQueryApi.class})
public class InterestQueryApiTest extends ApiTest {

    @MockBean
    private GetInterestsService getInterestsService;

    @Test
    void 관심사_목록을_조회한다() throws Exception {
        // given
        given(getInterestsService.invoke()).willReturn(
                new MultiInterestResponse(List.of(INTERST_1.응답(), INTERST_2.응답())));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/interest")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("interest/getInterests"));
        then(getInterestsService).should().invoke();
    }
}
