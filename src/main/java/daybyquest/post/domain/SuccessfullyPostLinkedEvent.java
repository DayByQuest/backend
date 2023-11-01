package daybyquest.post.domain;

import daybyquest.global.event.Event;
import lombok.Getter;

@Getter
public class SuccessfullyPostLinkedEvent implements Event {

    private final Long userId;

    private final Long questId;

    public SuccessfullyPostLinkedEvent(final Long userId, final Long questId) {
        this.userId = userId;
        this.questId = questId;
    }
}
