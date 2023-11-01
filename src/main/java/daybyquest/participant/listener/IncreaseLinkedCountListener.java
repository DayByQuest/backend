package daybyquest.participant.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import daybyquest.participant.domain.Participant;
import daybyquest.participant.domain.Participants;
import daybyquest.post.domain.SuccessfullyPostLinkedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class IncreaseLinkedCountListener {

    private final Participants participants;

    public IncreaseLinkedCountListener(final Participants participants) {
        this.participants = participants;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void listenSuccessfullyPostLinkedEvent(final SuccessfullyPostLinkedEvent event) {
        final Participant participant = participants.getByUserIdAndQuestId(event.getUserId(),
                event.getQuestId());
        participant.increaseLinkedCount();
    }
}
