package daybyquest.dislike.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import daybyquest.dislike.domain.PostDislikes;
import daybyquest.like.domain.PostLikedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeletePostDislikeListener {

    private final PostDislikes postDislikes;

    public DeletePostDislikeListener(final PostDislikes postDislikes) {
        this.postDislikes = postDislikes;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void listenPostLikedEvent(final PostLikedEvent event) {
        postDislikes.deleteByUserIdAndPostIdWithoutValidation(event.getUserId(), event.getPostId());
    }
}
