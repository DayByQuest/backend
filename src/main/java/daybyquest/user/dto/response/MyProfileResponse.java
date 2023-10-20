package daybyquest.user.dto.response;

import daybyquest.user.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyProfileResponse {

    private String username;

    private String name;

    private String imageUrl;

    private Long postCount;

    private Long followingCount;

    private Long followerCount;

    private MyProfileResponse(final String username, final String name, final String imageUrl,
            final Long postCount,
            final Long followingCount, final Long followerCount) {
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.postCount = postCount;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    public static MyProfileResponse of(final Profile profile, final String publicImageUrl) {
        return new MyProfileResponse(profile.getUsername(), profile.getName(), publicImageUrl,
                profile.getPostCount(), profile.getFollowingCount(), profile.getFollowerCount());
    }
}
