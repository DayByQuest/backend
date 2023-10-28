package daybyquest.like.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import daybyquest.dislike.domain.PostDislikedEvent;
import daybyquest.like.domain.PostLikes;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeletePostLikeListener {

    private final PostLikes postLikes;

    public DeletePostLikeListener(final PostLikes postLikes) {
        this.postLikes = postLikes;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void listenPostDislikedEvent(final PostDislikedEvent event) {
        postLikes.deleteByUserIdAndPostIdWithoutValidation(event.getUserId(), event.getPostId());
    }
}
