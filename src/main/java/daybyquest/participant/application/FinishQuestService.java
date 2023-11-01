package daybyquest.participant.application;

import daybyquest.participant.domain.Participant;
import daybyquest.participant.domain.Participants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FinishQuestService {

    private final Participants participants;

    public FinishQuestService(final Participants participants) {
        this.participants = participants;
    }

    @Transactional
    public void invoke(final Long loginId, final Long questId) {
        final Participant participant = participants.getByUserIdAndQuestId(loginId, questId);
        participant.finish();
    }
}
