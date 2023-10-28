package daybyquest.like.listener;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import daybyquest.like.application.DeletePostLikeService;
import daybyquest.like.domain.PostDislikedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeletePostLikeListener {

    private final DeletePostLikeService deletePostLikeService;

    public DeletePostLikeListener(final DeletePostLikeService deletePostLikeService) {
        this.deletePostLikeService = deletePostLikeService;
    }

    @Async
    @Transactional(propagation = REQUIRES_NEW)
    @TransactionalEventListener(fallbackExecution = true)
    public void listenPostDislikedEvent(final PostDislikedEvent event) {
        deletePostLikeService.invoke(event.getUserId(), event.getPostId());
    }
}
