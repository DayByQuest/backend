package daybyquest.image.domain;

import daybyquest.global.event.Event;
import java.io.InputStream;

public record ImageSavedEvent(String identifier, InputStream inputStream) implements Event {

}
