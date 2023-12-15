package daybyquest.support.fixture;

import static daybyquest.group.domain.GroupUserRole.MEMBER;

import daybyquest.group.dto.response.GroupUserResponse;
import daybyquest.group.query.GroupUserData;
import daybyquest.image.domain.Image;
import daybyquest.user.domain.User;
import daybyquest.user.query.Profile;
import org.springframework.test.util.ReflectionTestUtils;

public enum UserFixtures {

    ALICE("alice", "alice@email.com", "alice", "base.png"),
    BOB("bob", "bob@email.com", "bob", "bob.png"),
    CHARLIE("charlie", "charlie@email.com", "charlie", "charlie.png"),
    DAVID("david", "david@email.com", "david", "david.png"),
    DARTH("darth", "darth@email.com", "darth", "darth.png");

    public final String username;

    public final String email;

    public final String name;

    public final String imageIdentifier;

    UserFixtures(final String username, final String email, final String name, final String imageIdentifier) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.imageIdentifier = imageIdentifier;
    }

    public User 생성() {
        return 생성(null);
    }

    public User 생성(final Long id) {
        final User user = new User(username, email, name, new Image(imageIdentifier));
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    public Image 프로필_사진() {
        return new Image(imageIdentifier);
    }

    public Profile 프로필(final Long id) {
        return new Profile(id, username, name, imageIdentifier, 0L, false, false);
    }

    public GroupUserResponse 그룹원_응답(final Long id) {
        final GroupUserData groupUserData = new GroupUserData(id, username, name, imageIdentifier,
                0L, false, false, MEMBER);
        return GroupUserResponse.of(groupUserData);
    }
}
