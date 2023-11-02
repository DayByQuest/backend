package daybyquest.group.dto.response;

import daybyquest.group.query.GroupData;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupResponse {

    private Long id;

    private String name;

    private String description;

    private String interest;

    private String imageIdentifier;

    private Long userCount;

    private boolean isGroupManager;

    private GroupResponse(final Long id, final String name, final String description, final String interest,
            final String imageIdentifier, final Long userCount, final boolean isGroupManager) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interest = interest;
        this.imageIdentifier = imageIdentifier;
        this.userCount = userCount;
        this.isGroupManager = isGroupManager;
    }

    public static GroupResponse of(final GroupData groupData) {
        return new GroupResponse(groupData.getId(), groupData.getName(), groupData.getDescription(),
                groupData.getInterest(), groupData.getImageIdentifier(), groupData.getUserCount(),
                groupData.isGroupManager());
    }
}
