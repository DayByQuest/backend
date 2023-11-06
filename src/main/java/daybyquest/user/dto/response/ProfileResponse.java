package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;
import lombok.Getter;

@Getter
public class ProfileResponse {

    private final String username;

    private final String name;

    private final String imageIdentifier;

    private final Long postCount;

    private final boolean following;

    private final boolean blocking;

    private ProfileResponse(final String username, final String name, final String imageIdentifier,
            final Long postCount,
            final boolean following, final boolean blocking) {
        this.username = username;
        this.name = name;
        this.imageIdentifier = imageIdentifier;
        this.postCount = postCount;
        this.following = following;
        this.blocking = blocking;
    }

    public static ProfileResponse of(final Profile profile) {
        return new ProfileResponse(profile.getUsername(), profile.getName(), profile.getImageIdentifier(),
                profile.getPostCount(), profile.isFollowing(), profile.isBlocking());
    }
}
