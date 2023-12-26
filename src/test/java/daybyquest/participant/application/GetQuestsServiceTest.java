package daybyquest.participant.application;

import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetQuestsServiceTest extends ServiceTest {

    @Autowired
    private GetQuestsService getQuestsService;

    @Test
    void 수행_중인_퀘스트를_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();

        final Long badge1Id = badges.save(BADGE_1.생성()).getId();
        final Long badge2Id = badges.save(BADGE_2.생성()).getId();
        final Long badge3Id = badges.save(BADGE_3.생성()).getId();

        final Long quest1Id = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badge1Id)).getId();
        final Long quest2Id = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badge2Id)).getId();
        final Long quest3Id = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badge3Id)).getId();

        participants.saveWithUserIdAndQuestId(aliceId, quest1Id);
        participants.saveWithUserIdAndQuestId(aliceId, quest2Id);

        final List<Long> expected = List.of(quest1Id, quest2Id);

        // when
        final MultipleQuestsResponse response = getQuestsService.invoke(aliceId, DOING);
        final List<Long> actual = response.quests().stream().map(QuestResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
