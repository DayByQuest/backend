package daybyquest.participant.application;

import static daybyquest.global.error.ExceptionCode.NOT_FINISHABLE_QUEST;
import static daybyquest.participant.domain.ParticipantState.FINISHED;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.participant.domain.Participant;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FinishQuestServiceTest extends ServiceTest {

    @Autowired
    private FinishQuestService finishQuestService;

    @Autowired
    private TakeRewardService takeRewardService;

    @Autowired
    private ContinueQuestService continueQuestService;

    @Test
    void 퀘스트를_끝낸다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);
        퀘스트를_수행한다(aliceId, questId, QUEST_1.rewardCount);
        takeRewardService.invoke(aliceId, questId);
        continueQuestService.invoke(aliceId, questId);

        // when
        finishQuestService.invoke(aliceId, questId);
        final Participant actual = participants.getByUserIdAndQuestId(aliceId, questId);

        // then
        assertThat(actual.getState()).isEqualTo(FINISHED);
    }

    @Test
    void 이미_끝낸_퀘스트를_끝낼_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);
        퀘스트를_수행한다(aliceId, questId, QUEST_1.rewardCount);
        takeRewardService.invoke(aliceId, questId);

        // when & then
        assertThatThrownBy(() -> finishQuestService.invoke(aliceId, questId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FINISHABLE_QUEST.getMessage());
    }

    @Test
    void 보상을_받지_않은_퀘스트를_끝낼_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항이_설정된_일반_퀘스트_생성(badgeId)).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);
        퀘스트를_수행한다(aliceId, questId, QUEST_1.rewardCount);

        // when & then
        assertThatThrownBy(() -> finishQuestService.invoke(aliceId, questId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FINISHABLE_QUEST.getMessage());
    }
}
