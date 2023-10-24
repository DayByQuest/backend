package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponse {

    private String username;

    private String name;

    private String imageUrl;

    private Long postCount;

    private boolean following;

    private boolean blocking;

    private ProfileResponse(final String username, final String name, final String imageUrl,
            final Long postCount,
            final boolean following, final boolean blocking) {
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.postCount = postCount;
        this.following = following;
        this.blocking = blocking;
    }

    public static ProfileResponse of(final Profile profile, final String publicImageUrl) {
        return new ProfileResponse(profile.getUsername(), profile.getName(), publicImageUrl,
                profile.getPostCount(), profile.isFollowing(), profile.isBlocking());
    }
}
