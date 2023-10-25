package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyProfileResponse {

    private String username;

    private String name;

    private String imageIdentifier;

    private Long postCount;

    private Long followingCount;

    private Long followerCount;

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
