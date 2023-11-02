package daybyquest.group.query;

import daybyquest.image.vo.Image;
import lombok.Getter;

@Getter
public class GroupData {

    private final Long id;

    private final String name;

    private final String description;

    private final String interest;

    private final Image image;

    private final Long userCount;

    private final boolean isGroupManager;

    public GroupData(final Long id, final String name, final String description, final String interest,
            final Image image, final Long userCount, final boolean isGroupManager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interest = interest;
        this.image = image;
        this.userCount = userCount;
        this.isGroupManager = isGroupManager;
    }

    public String getImageIdentifier() {
        return image.getImageIdentifier();
    }
}
