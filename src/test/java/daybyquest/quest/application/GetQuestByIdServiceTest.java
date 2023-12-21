package daybyquest.quest.application;

import static daybyquest.quest.domain.QuestCategory.NORMAL;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetQuestByIdServiceTest extends ServiceTest {

    @Autowired
    private GetQuestByIdService getQuestByIdService;

    @Test
    void 퀘스트를_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();

        final Quest expected = QUEST_1.일반_퀘스트_생성(badgeId);
        QUEST_1.세부사항을_설정한다(expected);
        final Long questId = quests.save(expected).getId();

        // when
        final QuestResponse response = getQuestByIdService.invoke(aliceId, questId);

        // then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(questId);
            assertThat(response.category()).isEqualTo(NORMAL.toString());
            assertThat(response.title()).isEqualTo(QUEST_1.title);
            assertThat(response.content()).isEqualTo(QUEST_1.content);
            assertThat(response.interest()).isEqualTo(QUEST_1.interest);
            assertThat(response.rewardCount()).isEqualTo(QUEST_1.rewardCount);
            assertThat(response.imageIdentifier()).isEqualTo(BADGE_1.imageIdentifier);
        });
    }
}
