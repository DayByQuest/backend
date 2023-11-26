package daybyquest.group.query;

import daybyquest.group.domain.GroupUserRole;
import lombok.Getter;

@Getter
public class GroupUserData {

    private final Long id;

    private final String username;

    private final String name;

    private final String imageIdentifier;

    private final Long postCount;

    private final boolean following;

    private final boolean blocking;

    private final String role;

    public GroupUserData(final Long id, final String username, final String name,
            final String imageIdentifier, final Long postCount,
            final boolean following, final boolean blocking, final GroupUserRole role) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.imageIdentifier = imageIdentifier;
        this.postCount = postCount;
        this.following = following;
        this.blocking = blocking;
        this.role = role.toString();
    }
}
