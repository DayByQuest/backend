package daybyquest.participant.domain;

import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.GroupUsers;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.user.domain.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParticipantsTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private Users users;

    @Mock
    private Quests quests;

    @Mock
    private GroupUsers groupUsers;

    @InjectMocks
    private Participants participants;

    @Test
    void 사용자와_퀘스트_ID를_검증하고_저장한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long userId = 3L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);
        given(quests.getById(questId)).willReturn(quest);

        // when
        participants.saveWithUserIdAndQuestId(userId, questId);

        // then
        assertAll(() -> {
            then(users).should().validateExistentById(userId);
            then(quests).should().getById(questId);
            then(participantRepository).should().existsByUserIdAndQuestId(userId, questId);
            then(participantRepository).should().save(any(Participant.class));
        });
    }

    @Test
    void 이미_참여_중인_퀘스트_라면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long userId = 3L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);
        given(quests.getById(questId)).willReturn(quest);
        given(participantRepository.existsByUserIdAndQuestId(userId, questId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> participants.saveWithUserIdAndQuestId(userId, questId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 참여_시_그룹_퀘스트_라면_그룹에_속했는지를_검사한다() {
        // given
        final Long questId = 1L;
        final Long groupId = 2L;
        final Long userId = 3L;
        final Quest quest = QUEST_1.그룹_퀘스트_생성(questId, groupId);
        QUEST_1.보상_없이_세부사항을_설정한다(quest);
        given(quests.getById(questId)).willReturn(quest);

        // when
        participants.saveWithUserIdAndQuestId(userId, questId);

        // then
        then(groupUsers).should().validateExistentByUserIdAndGroupId(userId, groupId);
    }

    @Test
    void 사용자와_퀘스트_ID로_존재_여부를_검증한다() {
        // given
        final Long questId = 1L;
        final Long userId = 2L;
        given(participantRepository.existsByUserIdAndQuestId(userId, questId)).willReturn(true);

        // when
        participants.validateExistent(userId, questId);

        // then
        then(participantRepository).should().existsByUserIdAndQuestId(userId, questId);
    }

    @Test
    void 사용자와_퀘스트_ID로_존재_여부를_검증_시_없다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long userId = 2L;

        // when & then
        assertThatThrownBy(() -> participants.validateExistent(questId, userId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자와_퀘스트_ID를_통해_조회한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long userId = 3L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant expected = new Participant(userId, quest);
        given(participantRepository.findByUserIdAndQuestId(userId, questId)).willReturn(
                Optional.of(expected));

        // when
        final Participant actual = participants.getByUserIdAndQuestId(userId, questId);

        // then
        assertAll(() -> {
            then(participantRepository).should().findByUserIdAndQuestId(userId, questId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        });
    }

    @Test
    void 사용자와_퀘스트_ID를_통한_조회_시_없다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long userId = 3L;
        given(participantRepository.findByUserIdAndQuestId(userId, questId)).willReturn(
                Optional.empty());

        // when & then
        assertThatThrownBy(() -> participants.getByUserIdAndQuestId(userId, questId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자와_퀘스트_ID를_통해_삭제한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Long userId = 3L;
        final Quest quest = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        QUEST_1.세부사항을_설정한다(quest);

        final Participant expected = new Participant(userId, quest);
        given(participantRepository.findByUserIdAndQuestId(userId, questId)).willReturn(
                Optional.of(expected));

        // when
        participants.deleteByUserIdAndQuestId(userId, questId);

        // then
        then(participantRepository).should().delete(expected);
    }

    @Test
    void 사용자와_퀘스트_ID를_통한_삭제_시_없다면_예외를_던진다() {
        // given
        final Long questId = 1L;
        final Long userId = 3L;
        given(participantRepository.findByUserIdAndQuestId(userId, questId)).willReturn(
                Optional.empty());

        // when & then
        assertThatThrownBy(() -> participants.deleteByUserIdAndQuestId(userId, questId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 수행_중이거나_계속_중인_퀘스트_수를_검증한다() {
        // given
        final Long userId = 1L;
        given(participantRepository.countByUserIdAndState(userId, DOING)).willReturn(15);
        given(participantRepository.countByUserIdAndState(userId, CONTINUE)).willReturn(15);

        // when
        participants.validateCountByUserId(userId);

        // then
        assertAll(() -> {
            then(participantRepository).should().countByUserIdAndState(userId, DOING);
            then(participantRepository).should().countByUserIdAndState(userId, CONTINUE);
        });
    }

    @Test
    void 수행_중인_퀘스트가_15개를_초과하면_예외를_던진다() {
        // given
        final Long userId = 1L;
        given(participantRepository.countByUserIdAndState(userId, DOING)).willReturn(16);

        // when & then
        assertThatThrownBy(() -> participants.validateCountByUserId(userId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 계속_중인_퀘스트가_15개를_초과하면_예외를_던진다() {
        // given
        final Long userId = 1L;
        given(participantRepository.countByUserIdAndState(userId, DOING)).willReturn(15);
        given(participantRepository.countByUserIdAndState(userId, CONTINUE)).willReturn(16);

        // when & then
        assertThatThrownBy(() -> participants.validateCountByUserId(userId))
                .isInstanceOf(InvalidDomainException.class);
    }
}
