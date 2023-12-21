package daybyquest.quest.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.quest.dto.response.PageQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchQuestServiceTest extends ServiceTest {

    @Autowired
    private SearchQuestService searchQuestService;

    @Test
    void 퀘스트를_검색한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badge1Id = badges.save(BADGE_1.생성()).getId();
        final Long badge2Id = badges.save(BADGE_2.생성()).getId();
        final Long badge3Id = badges.save(BADGE_3.생성()).getId();

        final List<Long> expected = List.of(
                quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badge1Id))).getId(),
                quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badge2Id))).getId());
        quests.save(QUEST_2.세부사항을_설정한다(QUEST_2.일반_퀘스트_생성(badge3Id)));

        // when
        final PageQuestsResponse response = searchQuestService.invoke(aliceId, QUEST_1.title, 페이지());
        final List<Long> actual = response.quests().stream().map(QuestResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
