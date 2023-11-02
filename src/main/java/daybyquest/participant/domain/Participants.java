package daybyquest.participant.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_ACCEPTED_QUEST;
import static daybyquest.global.error.ExceptionCode.NOT_ACCEPTED_QUEST;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Participants {

    private final ParticipantRepository participantRepository;

    private final Users users;

    private final Quests quests;

    Participants(final ParticipantRepository participantRepository, final Users users,
            final Quests quests) {
        this.participantRepository = participantRepository;
        this.users = users;
        this.quests = quests;
    }

    public void saveWithUserIdAndQuestId(final Long userId, final Long questId) {
        users.validateExistentById(userId);
        final Quest quest = quests.getById(questId);
        final Participant participant = new Participant(userId, quest);
        validateNotExistent(participant);
        participantRepository.save(participant);
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
}
