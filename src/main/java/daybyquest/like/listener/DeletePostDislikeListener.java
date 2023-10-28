package daybyquest.like.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import daybyquest.like.application.DeletePostDislikeService;
import daybyquest.like.domain.PostLikedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeletePostDislikeListener {

    private final DeletePostDislikeService deletePostDislikeService;

    public DeletePostDislikeListener(final DeletePostDislikeService deletePostDislikeService) {
        this.deletePostDislikeService = deletePostDislikeService;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void listenPostLikedEvent(final PostLikedEvent event) {
        deletePostDislikeService.invoke(event.getUserId(), event.getPostId());
    }
}
