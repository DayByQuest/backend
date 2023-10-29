package daybyquest.support.fixture;

import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import org.springframework.test.util.ReflectionTestUtils;

public enum UserFixtures {

    ALICE("alice", "alice@email.com", "alice", "alice.png"),
    BOB("bob", "bob@email.com", "bob", "bob.png"),
    CHARLIE("charlie", "charlie@email.com", "charlie", "charlie.png"),
    DAVID("david", "david@email.com", "david", "david.png");

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
}
