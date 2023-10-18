package daybyquest.user.query;

import daybyquest.user.domain.Profile;

public interface ProfileDao {

    Profile getByUsername(final Long userId, final String username);

    Profile getMine(final Long userId);
}
