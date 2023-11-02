package daybyquest.badge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.badge.query.BadgeData;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadgeResponse {

    private String name;

    private String imageIdentifier;

    private Long id;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime acquiredAt;

    private BadgeResponse(final String name, final String imageIdentifier, final Long id,
            final LocalDateTime acquiredAt) {
        this.name = name;
        this.imageIdentifier = imageIdentifier;
        this.id = id;
        this.acquiredAt = acquiredAt;
    }

    public static BadgeResponse of(final BadgeData badgeData) {
        return new BadgeResponse(badgeData.getName(), badgeData.getImageIdentifier(), badgeData.getId(),
                badgeData.getAcquiredAt());
    }
}
