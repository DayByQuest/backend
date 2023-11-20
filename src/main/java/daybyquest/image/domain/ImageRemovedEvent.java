package daybyquest.image.domain;

import daybyquest.global.event.Event;

public record ImageRemovedEvent(String identifier) implements Event {

}
