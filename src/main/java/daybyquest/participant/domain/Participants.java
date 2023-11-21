package daybyquest.participant.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_ACCEPTED_QUEST;
import static daybyquest.global.error.ExceptionCode.EXCEED_MAX_QUEST;
import static daybyquest.global.error.ExceptionCode.NOT_ACCEPTED_QUEST;
import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.GroupUsers;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Participants {

    public static final int PROMOTE_THRESHOLD = 50;

    private static final int MAX_DOING_COUNT = 15;

    private static final int MAX_CONTINUE_COUNT = 15;

    private final ParticipantRepository participantRepository;

    private final Users users;

    private final Quests quests;

    private final GroupUsers groupUsers;

    Participants(final ParticipantRepository participantRepository, final Users users,
            final Quests quests, final GroupUsers groupUsers) {
        this.participantRepository = participantRepository;
        this.users = users;
        this.quests = quests;
        this.groupUsers = groupUsers;
    }

    public void saveWithUserIdAndQuestId(final Long userId, final Long questId) {
        users.validateExistentById(userId);
        final Quest quest = quests.getById(questId);
        validateGroupQuest(userId, quest);
        final Participant participant = new Participant(userId, quest);
        validateNotExistent(participant);
        participantRepository.save(participant);
    }

    private void validateGroupQuest(final Long userId, final Quest quest) {
        if (quest.isGroupQuest()) {
            groupUsers.validateExistentByUserIdAndGroupId(userId, quest.getGroupId());
        }
    }

    private void validateNotExistent(final Participant participant) {
        if (participantRepository.existsByUserIdAndQuestId(participant.getUserId(),
                participant.getQuestId())) {
            throw new InvalidDomainException(ALREADY_ACCEPTED_QUEST);
        }
    }

    public void validateExistent(final Long userId, final Long questId) {
        if (!participantRepository.existsByUserIdAndQuestId(userId,
                questId)) {
            throw new InvalidDomainException(NOT_ACCEPTED_QUEST);
        }
    }

    public Participant getByUserIdAndQuestId(final Long userId, final Long questId) {
        return participantRepository.findByUserIdAndQuestId(userId, questId)
                .orElseThrow(() -> new InvalidDomainException(NOT_ACCEPTED_QUEST));
    }

    public void deleteByUserIdAndQuestId(final Long userId, final Long questId) {
        final Participant participant = getByUserIdAndQuestId(userId, questId);
        participantRepository.delete(participant);
    }

    public void validateCountByUserId(final Long userId) {
        if (participantRepository.countByUserIdAndState(userId, DOING) > MAX_DOING_COUNT
                || participantRepository.countByUserIdAndState(userId, CONTINUE) > MAX_CONTINUE_COUNT) {
            throw new InvalidDomainException(EXCEED_MAX_QUEST);
        }
    }

    public void increaseLinkedCount(final Long userId, final Long questId) {
        final Participant participant = getByUserIdAndQuestId(userId, questId);
        participant.increaseLinkedCount();
        if (participant.getLinkedCount() == PROMOTE_THRESHOLD) {
            final User user = users.getById(userId);
            user.promote();
        }
    }
}
