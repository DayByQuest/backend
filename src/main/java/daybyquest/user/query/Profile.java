package daybyquest.user.query;

import lombok.Getter;

@Getter
public class Profile {

    private final Long id;

    private final String username;

    private final String name;

    private final String imageIdentifier;

    private final Long postCount;

    private final boolean following;

    private final boolean blocking;

    private final Long followingCount;

    private final Long followerCount;


    private Profile(final Long id, final String username, final String name, final String imageIdentifier,
            final Long postCount, final boolean following, final boolean blocking, final Long followingCount,
            final Long followerCount) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imageIdentifier = imageIdentifier;
        this.postCount = postCount;
        this.following = following;
        this.blocking = blocking;
        this.followingCount = followingCount;
        this.followerCount = followerCount;
    }

    public Profile(final Long id, final String username, final String name, final String imageIdentifier,
            final Long postCount,
            final boolean following, final boolean blocking) {
        this(id, username, name, imageIdentifier, postCount, following, blocking, 0L, 0L);
    }

    public Profile(final Long id, final String username, final String name, final String imageIdentifier,
            final Long postCount, final Long followingCount, final Long followerCount) {
        this(id, username, name, imageIdentifier, postCount, false, false, followingCount, followerCount);
    }
}
