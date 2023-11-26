package daybyquest.user.dto.response;

import daybyquest.user.query.Profile;

public record ProfileResponse(String username, String name, String imageIdentifier, Long postCount,
                              boolean following, boolean blocking) {

    public static ProfileResponse of(final Profile profile) {
        return new ProfileResponse(profile.getUsername(), profile.getName(), profile.getImageIdentifier(),
                profile.getPostCount(), profile.isFollowing(), profile.isBlocking());
    }
}
