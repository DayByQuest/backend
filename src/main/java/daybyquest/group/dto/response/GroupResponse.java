package daybyquest.group.dto.response;

import daybyquest.group.query.GroupData;

public record GroupResponse(Long id, String name, String description, String interest, String imageIdentifier,
                            Long userCount, boolean isGroupManager) {

    public static GroupResponse of(final GroupData groupData) {
        return new GroupResponse(groupData.getId(), groupData.getName(), groupData.getDescription(),
                groupData.getInterest(), groupData.getImageIdentifier(), groupData.getUserCount(),
                groupData.isGroupManager());
    }
}
