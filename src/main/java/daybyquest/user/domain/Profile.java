package daybyquest.user.domain;

import lombok.Getter;

@Getter
public class Profile {

    private final Long id;

    private final String username;

    private final String name;

    private final String imageUrl;

    private final Long postCount;

    private final boolean following;

    private final boolean blocking;

    public Profile(final Long id, final String username, final String name, final String imageUrl, final Long postCount,
        final boolean following, final boolean blocking) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imageUrl = imageUrl;
        this.postCount = postCount;
        this.following = following;
        this.blocking = blocking;
    }
}
