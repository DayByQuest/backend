package daybyquest.participant.domain;

import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.participant.domain.ParticipantState.FINISHED;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_WITHOUT_REWARD;
import static daybyquest.support.util.ParticipantUtils.게시물_연결_횟수를_지정한다;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_계속한다;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_끝낸다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.quest.domain.Quest;
import org.junit.jupiter.api.Test;

public class ParticipantTest {

    @Test
    void 퀘스트_참여_시_진행_중_상태가_설정된다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        // when
        final Participant participant = new Participant(userId, quest);

        // then
        assertThat(participant.getState()).isEqualTo(DOING);
    }

    @Test
    void 퀘스트_참여_시_연결된_게시물_수는_0개로_설정된다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        // when
        final Participant participant = new Participant(userId, quest);

        // then
        assertThat(participant.getLinkedCount()).isZero();
    }

    @Test
    void 퀘스트_참여_시_활성화된_퀘스트가_아니라면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);

        // when & then
        assertThatThrownBy(() -> new Participant(userId, quest))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_ID를_조회한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        // when
        final Participant participant = new Participant(userId, quest);

        // then
        assertThat(participant.getQuestId()).isEqualTo(questId);
    }

    @Test
    void 퀘스트_보상을_받는다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);
        final Participant participant = new Participant(userId, quest);
        게시물_연결_횟수를_지정한다(participant, QUEST_1.rewardCount);

        // when
        final Long actualBadgeId = participant.takeReward();

        // then
        assertAll(() -> {
            assertThat(actualBadgeId).isEqualTo(badgeId);
            assertThat(participant.getState()).isEqualTo(FINISHED);
        });
    }

    @Test
    void 퀘스트_목표치를_달성하지_못했다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(userId, quest);
        게시물_연결_횟수를_지정한다(participant, QUEST_1.rewardCount - 1);

        // when & then
        assertThatThrownBy(participant::takeReward)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_보상이_없다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long userId = 1L;
        final Quest quest = QUEST_WITHOUT_REWARD.일반_퀘스트_생성(questId, null);
        QUEST_1.보상_없이_세부사항을_설정한다(quest);

        final Participant participant = new Participant(userId, quest);
        게시물_연결_횟수를_지정한다(participant, QUEST_1.rewardCount);

        // when & then
        assertThatThrownBy(participant::takeReward)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 이미_보상을_받았다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;
        final Long aliceId = 4L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant bob = new Participant(bobId, quest);
        final Participant alice = new Participant(aliceId, quest);

        퀘스트를_계속한다(bob);
        퀘스트를_끝낸다(bob);

        // when & then
        assertAll(() -> {
            assertThatThrownBy(bob::takeReward).isInstanceOf(InvalidDomainException.class);
            assertThatThrownBy(alice::takeReward).isInstanceOf(InvalidDomainException.class);
        });
    }

    @Test
    void 퀘스트가_계속_상태일_때_끝낸다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);

        퀘스트를_계속한다(participant);

        // when
        participant.finish();

        // then
        assertThat(participant.getState()).isEqualTo(FINISHED);
    }

    @Test
    void 그룹_퀘스트면_1회_수행_후_끝낼_수_있다() {
        // given
        final Long questId = 1L;
        final Long groupId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.그룹_퀘스트_생성(questId, groupId);
        QUEST_1.보상_없이_세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);
        게시물_연결_횟수를_지정한다(participant, 1L);

        // when
        participant.finish();

        // then
        assertThat(participant.getState()).isEqualTo(FINISHED);
    }

    @Test
    void 퀘스트가_계속_상태가_아닐_때_끝내려하면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;
        final Long aliceId = 4L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant bob = new Participant(bobId, quest);
        final Participant alice = new Participant(aliceId, quest);

        퀘스트를_끝낸다(bob);

        // when & then
        assertAll(() -> {
            assertThatThrownBy(bob::finish).isInstanceOf(InvalidDomainException.class);
            assertThatThrownBy(alice::finish).isInstanceOf(InvalidDomainException.class);
        });
    }

    @Test
    void 퀘스트가_끝난_상태일_때_계속한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);

        퀘스트를_끝낸다(participant);

        // when
        participant.doContinue();

        // then
        assertThat(participant.getState()).isEqualTo(CONTINUE);
    }

    @Test
    void 퀘스트가_끝난_상태가_아닐_때_계속하려_하면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;
        final Long aliceId = 4L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant bob = new Participant(bobId, quest);
        final Participant alice = new Participant(aliceId, quest);

        퀘스트를_계속한다(bob);

        // when & then
        assertAll(() -> {
            assertThatThrownBy(bob::doContinue).isInstanceOf(InvalidDomainException.class);
            assertThatThrownBy(alice::doContinue).isInstanceOf(InvalidDomainException.class);
        });
    }

    @Test
    void 퀘스트_수행_횟수를_증가시킨다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);

        // when
        participant.increaseLinkedCount();

        // then
        assertThat(participant.getLinkedCount()).isOne();
    }

    @Test
    void 퀘스트가_계속_상태일_때_퀘스트_수행_횟수를_증가시킨다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);
        퀘스트를_계속한다(participant);

        // when
        participant.increaseLinkedCount();

        // then
        assertThat(participant.getLinkedCount()).isOne();
    }

    @Test
    void 퀘스트가_끝난_상태라면_수행_횟수를_증가시킬_수_없다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long bobId = 3L;

        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant participant = new Participant(bobId, quest);
        퀘스트를_끝낸다(participant);

        // when & then
        assertThatThrownBy(participant::increaseLinkedCount).isInstanceOf(InvalidDomainException.class);
    }
}
