package daybyquest.quest.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.SaveQuestDetailRequest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class SaveQuestDetailServiceTest extends ServiceTest {

    @Autowired
    private SaveQuestDetailService saveQuestDetailService;

    @Test
    void 퀘스트_세부사항을_설정한다() {
        // given
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.일반_퀘스트_생성(badgeId)).getId();
        
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);
        final SaveQuestDetailRequest request = 퀘스트_세부사항_설정_요청(quest);

        // when
        saveQuestDetailService.invoke(questId, request);

        // then
        final Quest actual = quests.getById(questId);
        assertAll(() -> {
            assertThat(actual.getId()).isEqualTo(questId);
            assertThat(actual.getTitle()).isEqualTo(QUEST_1.title);
            assertThat(actual.getContent()).isEqualTo(QUEST_1.content);
            assertThat(actual.getInterestName()).isEqualTo(QUEST_1.interest);
            assertThat(actual.getLabel()).isEqualTo(QUEST_1.label);
            assertThat(actual.getRewardCount()).isEqualTo(QUEST_1.rewardCount);
        });
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
}
