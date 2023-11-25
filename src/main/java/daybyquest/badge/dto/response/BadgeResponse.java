package daybyquest.badge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.badge.query.BadgeData;
import java.time.LocalDateTime;

public record BadgeResponse(String name, String imageIdentifier, Long id,
                            @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime acquiredAt) {

    public static BadgeResponse of(final BadgeData badgeData) {
        return new BadgeResponse(badgeData.getName(), badgeData.getImageIdentifier(), badgeData.getId(),
                badgeData.getAcquiredAt());
    }
}
