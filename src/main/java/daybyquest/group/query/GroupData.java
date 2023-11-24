package daybyquest.group.query;

import static daybyquest.group.domain.GroupUserRole.MANAGER;

import daybyquest.group.domain.GroupUserRole;
import daybyquest.image.domain.Image;
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

    private final boolean isGroupMember;

    public GroupData(final Long id, final String name, final String description, final String interest,
            final Image image, final Long userCount, final GroupUserRole role) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interest = interest;
        this.image = image;
        this.userCount = userCount;
        if (role == null) {
            isGroupManager = false;
            isGroupMember = false;
            return;
        }
        if (role == MANAGER) {
            isGroupManager = true;
            isGroupMember = true;
            return;
        }
        isGroupManager = false;
        isGroupMember = true;
    }

    public String getImageIdentifier() {
        return image.getIdentifier();
    }
}
