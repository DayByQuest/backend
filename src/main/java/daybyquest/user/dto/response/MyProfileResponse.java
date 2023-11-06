package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;
import lombok.Getter;

@Getter
public class MyProfileResponse {

    private final String username;

    private final String name;

    private final String imageIdentifier;

    private final Long postCount;

    private final Long followingCount;

    private final Long followerCount;

    private MyProfileResponse(final String username, final String name, final String imageIdentifier,
            final Long postCount, final Long followingCount, final Long followerCount) {
        this.username = username;
        this.name = name;
        this.imageIdentifier = imageIdentifier;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    public static MyProfileResponse of(final Profile profile) {
        return new MyProfileResponse(profile.getUsername(), profile.getName(), profile.getImageIdentifier(),
                profile.getPostCount(), profile.getFollowingCount(), profile.getFollowerCount());
    }
}
