package daybyquest.like.domain;

import daybyquest.global.event.Event;
import lombok.Getter;

@Getter
public class PostDislikedEvent implements Event {

    private final Long userId;

    private final Long postId;

    public PostDislikedEvent(final Long userId, final Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
