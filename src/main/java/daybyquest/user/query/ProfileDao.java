package daybyquest.user.query;

import daybyquest.user.domain.Profile;
import java.util.Collection;
import java.util.List;

public interface ProfileDao {

    Profile getByUsername(final Long userId, final String username);

    Profile getMine(final Long userId);

    List<Profile> findAllByUserIdIn(final Long userId, final Collection<Long> targetIds);
}
