package daybyquest.like.listener;

import daybyquest.like.application.DeletePostLikeService;
import daybyquest.like.domain.PostDislikedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DeletePostLikeListener {

    private final DeletePostLikeService deletePostLikeService;

    public DeletePostLikeListener(final DeletePostLikeService deletePostLikeService) {
        this.deletePostLikeService = deletePostLikeService;
    }

    @EventListener
    public void listenPostDislikedEvent(final PostDislikedEvent event) {
        deletePostLikeService.invoke(event.getUserId(), event.getPostId());
    }
}
