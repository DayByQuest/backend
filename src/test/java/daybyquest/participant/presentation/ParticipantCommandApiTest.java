package daybyquest.participant.presentation;

import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.participant.application.ContinueQuestService;
import daybyquest.participant.application.DeleteParticipantService;
import daybyquest.participant.application.FinishQuestService;
import daybyquest.participant.application.SaveParticipantService;
import daybyquest.participant.application.TakeRewardService;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({ParticipantCommandApi.class})
public class ParticipantCommandApiTest extends ApiTest {

    @MockBean
    private SaveParticipantService saveParticipantService;

    @MockBean
    private DeleteParticipantService deleteParticipantService;

    @MockBean
    private TakeRewardService takeRewardService;

    @MockBean
    private FinishQuestService finishQuestService;

    @MockBean
    private ContinueQuestService continueQuestService;

    @Test
    void 퀘스트를_수락한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/quest/{questId}/accept", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/saveParticipant"));
        then(saveParticipantService).should().invoke(로그인_ID, questId);
    }

    @Test
    void 퀘스트를_삭제한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                delete("/quest/{questId}/accept", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/deleteParticipant"));
        then(deleteParticipantService).should().invoke(로그인_ID, questId);
    }

    @Test
    void 퀘스트_보상을_받는다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/quest/{questId}/reward", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/takeReward"));
        then(takeRewardService).should().invoke(로그인_ID, questId);
    }

    @Test
    void 퀘스트를_완료한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/quest/{questId}/finish", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/finish"));
        then(finishQuestService).should().invoke(로그인_ID, questId);
    }

    @Test
    void 퀘스트를_계속한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/quest/{questId}/continue", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("participant/continue"));
        then(continueQuestService).should().invoke(로그인_ID, questId);
    }
}
