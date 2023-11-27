package daybyquest.quest.presentation;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import daybyquest.quest.application.group.SaveGroupQuestDetailService;
import daybyquest.quest.application.group.SaveGroupQuestService;
import daybyquest.quest.application.group.SubscribeGroupQuestLabelsService;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.SaveGroupQuestDetailRequest;
import daybyquest.quest.dto.request.SaveGroupQuestRequest;
import daybyquest.support.test.ApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest({GroupQuestCommandApi.class})
public class GroupQuestCommandApiTest extends ApiTest {

    @MockBean
    private SaveGroupQuestService saveGroupQuestService;

    @MockBean
    private SaveGroupQuestDetailService saveGroupQuestDetailService;

    @MockBean
    private SubscribeGroupQuestLabelsService subscribeGroupQuestLabelsService;

    @Test
    void 그룹_퀘스트를_생성한다() throws Exception {
        // given
        final Long questId = 2L;
        final Long groupId = 3L;
        given(saveGroupQuestService.invoke(eq(로그인_ID), any(), any())).willReturn(questId);
        final SaveGroupQuestRequest saveGroupQuestRequest = 퀘스트_생성_요청(QUEST_1.그룹_퀘스트_생성(questId, groupId));
        final MockMultipartFile file1 = 사진을_전송한다("images");
        final MockMultipartFile file2 = 사진을_전송한다("images");
        final MockMultipartFile file3 = 사진을_전송한다("images");
        final MockMultipartFile request = 멀티파트_JSON을_전송한다("request", saveGroupQuestRequest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                multipart("/group/quest")
                        .file(file1)
                        .file(file2)
                        .file(file3)
                        .file(request)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/saveGroupQuest",
                        requestParts(partWithName("images").description("사진 파일(3개)"),
                                partWithName("request").description("요청")))
                );
        then(saveGroupQuestService).should().invoke(eq(로그인_ID), any(), any());
    }

    @Test
    void 그룹_퀘스트_세부사항을_설정한다() throws Exception {
        // given
        final Long questId = 2L;
        final Long groupId = 3L;
        final Quest quest = QUEST_1.그룹_퀘스트_생성(questId, groupId);
        QUEST_1.보상_없이_세부사항을_설정한다(quest);
        final SaveGroupQuestDetailRequest request = 퀘스트_세부사항_설정_요청(quest);

        // when
        final ResultActions resultActions = mockMvc.perform(
                post("/group/{questId}/quest/detail", questId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/saveGroupQuestDetail"));
        then(saveGroupQuestDetailService).should().invoke(eq(로그인_ID), eq(questId), any());
    }

    @Test
    void 그룹_퀘스트_라벨을_구독한다() throws Exception {
        // given
        final Long questId = 2L;

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/group/{questId}/quest/labels", questId)
                        .header(인증_헤더_이름, 사용자_인증_헤더));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andDo(인증_상태로_문서화한다("quest/subscribeGroupQuestLabel"));
        then(subscribeGroupQuestLabelsService).should().invoke(로그인_ID, questId);
    }

    private SaveGroupQuestRequest 퀘스트_생성_요청(final Quest quest) {
        final SaveGroupQuestRequest request = new SaveGroupQuestRequest();
        ReflectionTestUtils.setField(request, "groupId", quest.getGroupId());
        ReflectionTestUtils.setField(request, "imageDescription", quest.getImageDescription());
        return request;
    }

    private SaveGroupQuestDetailRequest 퀘스트_세부사항_설정_요청(final Quest quest) {
        final SaveGroupQuestDetailRequest request = new SaveGroupQuestDetailRequest();
        ReflectionTestUtils.setField(request, "title", quest.getTitle());
        ReflectionTestUtils.setField(request, "content", quest.getContent());
        ReflectionTestUtils.setField(request, "interest", quest.getInterestName());
        ReflectionTestUtils.setField(request, "label", quest.getLabel());
        ReflectionTestUtils.setField(request, "expiredAt", quest.getExpiredAt());
        return request;
    }
}
