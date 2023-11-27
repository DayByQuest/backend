package daybyquest.quest.presentation;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.quest.application.group.GetGroupQuestsService;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({GroupQuestQueryApi.class})
public class GroupQuestQueryApiTest extends ApiTest {

    @MockBean
    private GetGroupQuestsService getGroupQuestsService;

    @Test
    void 퀘스트를_추천받는다() throws Exception {
        // given
        final Long groupId = 2L;
        given(getGroupQuestsService.invoke(로그인_ID, groupId))
                .willReturn(new MultipleQuestsResponse(
                        List.of(QUEST_1.그룹_응답(2L, groupId), QUEST_2.그룹_응답(3L, groupId))));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{groupId}/quest", groupId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/getGroupQuests"));
        then(getGroupQuestsService).should().invoke(로그인_ID, groupId);
    }
}
