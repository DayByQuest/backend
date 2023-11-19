package daybyquest.participant.application;

import daybyquest.participant.domain.Participants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveParticipantService {

    private final Participants participants;

    public SaveParticipantService(final Participants participants) {
        this.participants = participants;
    }

    @Transactional
    public void invoke(final Long loginId, final Long questId) {
        participants.saveWithUserIdAndQuestId(loginId, questId);
        participants.validateCountByUserId(loginId);
    }
}

