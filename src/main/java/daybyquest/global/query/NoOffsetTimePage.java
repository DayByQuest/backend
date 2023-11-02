package daybyquest.global.query;

import java.time.LocalDateTime;

public record NoOffsetTimePage(LocalDateTime lastTime, int limit) {

}
