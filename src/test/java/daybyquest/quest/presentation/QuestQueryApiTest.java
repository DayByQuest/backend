package daybyquest.quest.presentation;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.quest.application.GetQuestByIdService;
import daybyquest.quest.application.GetQuestImagesService;
import daybyquest.quest.application.RecommendQuestsService;
import daybyquest.quest.application.SearchQuestService;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.PageQuestsResponse;
import daybyquest.quest.dto.response.QuestImagesResponse;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({QuestQueryApi.class})
public class QuestQueryApiTest extends ApiTest {

    @MockBean
    private GetQuestByIdService getQuestByIdService;

    @MockBean
    private GetQuestImagesService getQuestImagesService;

    @MockBean
    private RecommendQuestsService recommendQuestsService;

    @MockBean
    private SearchQuestService searchQuestService;

    @Test
    void 퀘스트_프로필을_조회한다() throws Exception {
        // given
        final Long questId = 2L;
        given(getQuestByIdService.invoke(로그인_ID, questId)).willReturn(QUEST_1.응답(questId, 3L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest/{questId}", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/getQuest"));
        then(getQuestByIdService).should().invoke(로그인_ID, questId);
    }

    @Test
    void 퀘스트_예시_사진을_조회한다() throws Exception {
        // given
        final Long questId = 2L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, 3L);
        given(getQuestImagesService.invoke(questId)).willReturn(QuestImagesResponse.of(quest));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest/{questId}/image", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/getQuestImages"));
        then(getQuestImagesService).should().invoke(questId);
    }

    @Test
    void 퀘스트를_추천받는다() throws Exception {
        // given
        given(recommendQuestsService.invoke(로그인_ID))
                .willReturn(new MultipleQuestsResponse(
                        List.of(QUEST_1.응답(2L, 3L), QUEST_2.응답(3L, 4L))));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest/recommendation")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/recommendQuest"));
        then(recommendQuestsService).should().invoke(로그인_ID);
    }

    @Test
    void 퀘스트를_검색한다() throws Exception {
        // given
        final String 키워드 = "키워드";
        given(searchQuestService.invoke(eq(로그인_ID), eq(키워드), any()))
                .willReturn(new PageQuestsResponse(
                        List.of(QUEST_1.응답(2L, 3L), QUEST_2.응답(3L, 4L)), 3L));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/search/quest")
                        .param("keyword", 키워드)
                        .param("limit", "5")
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/searchQuest"));
        then(searchQuestService).should().invoke(eq(로그인_ID), eq(키워드), any());
    }
}
