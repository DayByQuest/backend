package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;

public record MyProfileResponse(String username, String name, String imageIdentifier, Long postCount,
                                Long followingCount, Long followerCount) {

    public static MyProfileResponse of(final Profile profile) {
        return new MyProfileResponse(profile.getUsername(), profile.getName(), profile.getImageIdentifier(),
                profile.getPostCount(), profile.getFollowingCount(), profile.getFollowerCount());
    }
}
