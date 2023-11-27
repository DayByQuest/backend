package daybyquest.participant.presentation;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.participant.application.GetQuestsService;
import daybyquest.participant.domain.ParticipantState;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({ParticipantQueryApi.class})
public class ParticipantQueryApiTest extends ApiTest {

    @MockBean
    private GetQuestsService getQuestsService;

    @Test
    void 내_퀘스트_목록을_조회한다() throws Exception {
        // given
        final Long questId = 2L;
        given(getQuestsService.invoke(로그인_ID, ParticipantState.DOING))
                .willReturn(new MultipleQuestsResponse(
                        List.of(QUEST_1.응답(2L, 3L), QUEST_2.응답(3L, 4L))));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest", questId)
                        .param("state", "DOING")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/getMyQuests"));
        then(getQuestsService).should().invoke(로그인_ID, ParticipantState.DOING);
    }
}
