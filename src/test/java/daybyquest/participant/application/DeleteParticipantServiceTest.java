package daybyquest.participant.application;

import static daybyquest.global.error.ExceptionCode.NOT_ACCEPTED_QUEST;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteParticipantServiceTest extends ServiceTest {

    @Autowired
    private DeleteParticipantService deleteParticipantService;

    @Test
    void 퀘스트를_삭제한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        // when
        deleteParticipantService.invoke(aliceId, questId);

        // then
        assertThatThrownBy(() -> participants.getByUserIdAndQuestId(aliceId, questId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_ACCEPTED_QUEST.getMessage());
    }

    @Test
    void 수락한_적이_없는_퀘스트는_삭제할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();

        // when & then
        assertThatThrownBy(() -> deleteParticipantService.invoke(aliceId, questId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_ACCEPTED_QUEST.getMessage());
    }
}
