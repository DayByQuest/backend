package daybyquest.participant.listener;

import daybyquest.participant.domain.Participants;
import daybyquest.post.domain.SuccessfullyPostLinkedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IncreaseLinkedCountListener {

    private final Participants participants;

    public IncreaseLinkedCountListener(final Participants participants) {
        this.participants = participants;
    }

    @Transactional
    @EventListener
    public void listenSuccessfullyPostLinkedEvent(final SuccessfullyPostLinkedEvent event) {
        participants.increaseLinkedCount(event.getUserId(), event.getQuestId());
    }
}
