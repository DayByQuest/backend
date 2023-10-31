package daybyquest.participant.application;

import daybyquest.participant.domain.Participants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteParticipantService {

    private final Participants participants;

    public DeleteParticipantService(final Participants participants) {
        this.participants = participants;
    }

    @Transactional
    public void invoke(final Long loginId, final Long questId) {
        participants.deleteByUserIdAndQuestId(loginId, questId);
    }
}
