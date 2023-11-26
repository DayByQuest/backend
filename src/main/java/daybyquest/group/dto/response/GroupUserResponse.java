package daybyquest.group.dto.response;

import daybyquest.group.query.GroupUserData;

public record GroupUserResponse(String username, String name, String imageIdentifier, Long postCount,
                                boolean following, boolean blocking, String role) {

    public static GroupUserResponse of(final GroupUserData groupUserData) {
        return new GroupUserResponse(groupUserData.getUsername(), groupUserData.getName(),
                groupUserData.getImageIdentifier(), groupUserData.getPostCount(), groupUserData.isFollowing(),
                groupUserData.isBlocking(), groupUserData.getRole());
    }
}
