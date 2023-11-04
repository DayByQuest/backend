package daybyquest.badge.query;

import daybyquest.image.domain.Image;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class BadgeData {

    private final Long id;

    private final String name;

    private final Image image;

    private final LocalDateTime acquiredAt;

    public BadgeData(final Long id, final String name, final Image image, final LocalDateTime acquiredAt) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.acquiredAt = acquiredAt;
    }

    public String getImageIdentifier() {
        return image.getIdentifier();
    }
}
