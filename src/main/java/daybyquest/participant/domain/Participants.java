package daybyquest.participant.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_ACCEPTED_QUEST;
import static daybyquest.global.error.ExceptionCode.NOT_ACCEPTED_QUEST;

import daybyquest.global.error.exception.InvalidDomainException;
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

    public void save(final Participant participant) {
        users.validateExistentById(participant.getUserId());
        quests.validateExistentById(participant.getQuestId());
        validateNotExistent(participant);
        participantRepository.save(participant);
    }

    private void validateNotExistent(final Participant participant) {
        if (participantRepository.existsByUserIdAndQuestId(participant.getUserId(),
                participant.getQuestId())) {
            throw new InvalidDomainException(ALREADY_ACCEPTED_QUEST);
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
