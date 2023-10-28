package daybyquest.like.listener;

import daybyquest.like.application.DeletePostDislikeService;
import daybyquest.like.domain.PostLikedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DeletePostDislikeListener {

    private final DeletePostDislikeService deletePostDislikeService;

    public DeletePostDislikeListener(final DeletePostDislikeService deletePostDislikeService) {
        this.deletePostDislikeService = deletePostDislikeService;
    }

    @EventListener
    public void listenPostLikedEvent(final PostLikedEvent event) {
        deletePostDislikeService.invoke(event.getUserId(), event.getPostId());
    }
}
