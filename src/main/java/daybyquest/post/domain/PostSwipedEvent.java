package daybyquest.post.domain;

import daybyquest.global.event.Event;
import lombok.Getter;

@Getter
public class PostSwipedEvent extends Event {

    private final Long userId;

    private final Long postId;

    public PostSwipedEvent(final Long userId, final Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
