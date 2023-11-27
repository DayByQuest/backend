package daybyquest.quest.presentation;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.quest.application.SaveQuestDetailService;
import daybyquest.quest.application.SaveQuestService;
import daybyquest.quest.application.SendQuestLabelsService;
import daybyquest.quest.application.SubscribeQuestLabelsService;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.QuestLabelsRequest;
import daybyquest.quest.dto.request.SaveQuestDetailRequest;
import daybyquest.quest.dto.request.SaveQuestRequest;
import daybyquest.support.test.ApiTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({QuestCommandApi.class})
public class QuestCommandApiTest extends ApiTest {

    @MockBean
    private SaveQuestService saveQuestService;

    @MockBean
    private SaveQuestDetailService saveQuestDetailService;

    @MockBean
    private SubscribeQuestLabelsService subscribeQuestLabelsService;

    @MockBean
    private SendQuestLabelsService sendQuestLabelsService;

    @Test
    void 퀘스트를_생성한다() throws Exception {
        // given
        final Long questId = 2L;
        given(saveQuestService.invoke(any(), any())).willReturn(questId);
        final SaveQuestRequest saveQuestRequest = 퀘스트_생성_요청(QUEST_1.일반_퀘스트_생성(questId, 3L));
        final MockMultipartFile file1 = 사진을_전송한다("images");
        final MockMultipartFile file2 = 사진을_전송한다("images");
        final MockMultipartFile file3 = 사진을_전송한다("images");
        final MockMultipartFile request = 멀티파트_JSON을_전송한다("request", saveQuestRequest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/quest")
                        .file(file1)
                        .file(file2)
                        .file(file3)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/saveNormalQuest",
                        requestParts(partWithName("images").description("사진 파일(3개)"),
                                partWithName("request").description("요청")))
                );
        then(saveQuestService).should().invoke(any(), any());
    }

    @Test
    void 퀘스트_세부사항을_설정한다() throws Exception {
        // given
        final Long questId = 2L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, 3L);
        QUEST_1.세부사항을_설정한다(quest);
        final SaveQuestDetailRequest request = 퀘스트_세부사항_설정_요청(quest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/quest/{questId}/detail", questId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/saveQuestDetail"));
        then(saveQuestDetailService).should().invoke(eq(questId), any());
    }

    @Test
    void 퀘스트_라벨을_구독한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/quest/{questId}/labels", questId)
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/subscribeQuestLabel"));
        then(subscribeQuestLabelsService).should().invoke(eq(questId));
    }

    @Test
    void 퀘스트_라벨_리스트를_전송한다() throws Exception {
        // given
        final Long questId = 2L;
        final QuestLabelsRequest request = 퀘스트_라벨리스트_요청();

        // when
        final ResultActions resultActions = mockMvc.perform(
                patch("/quest/{questId}/shot", questId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(인증_헤더_이름, 어드민_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/sendQuestLabels"));
        then(sendQuestLabelsService).should().invoke(eq(questId), any());
    }

    private SaveQuestRequest 퀘스트_생성_요청(final Quest quest) {
        final SaveQuestRequest request = new SaveQuestRequest();
        ReflectionTestUtils.setField(request, "badgeId", quest.getBadgeId());
        ReflectionTestUtils.setField(request, "imageDescription", quest.getImageDescription());
        return request;
    }

    private SaveQuestDetailRequest 퀘스트_세부사항_설정_요청(final Quest quest) {
        final SaveQuestDetailRequest request = new SaveQuestDetailRequest();
        ReflectionTestUtils.setField(request, "title", quest.getTitle());
        ReflectionTestUtils.setField(request, "content", quest.getContent());
        ReflectionTestUtils.setField(request, "interest", quest.getInterestName());
        ReflectionTestUtils.setField(request, "label", quest.getLabel());
        ReflectionTestUtils.setField(request, "rewardCount", quest.getRewardCount());
        return request;
    }

    private QuestLabelsRequest 퀘스트_라벨리스트_요청() {
        final QuestLabelsRequest request = new QuestLabelsRequest();
        ReflectionTestUtils.setField(request, "labels", List.of("라벨1", "라벨2", "라벨3"));
        return request;
    }
}
