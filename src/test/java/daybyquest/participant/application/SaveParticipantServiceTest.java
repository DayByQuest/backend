package daybyquest.participant.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThatCode;

import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveParticipantServiceTest extends ServiceTest {

    @Autowired
    private SaveParticipantService saveParticipantService;

    @Test
    void 퀘스트를_수락한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();

        // when & then
        assertThatCode(() -> saveParticipantService.invoke(aliceId, questId))
                .doesNotThrowAnyException();
    }
}
