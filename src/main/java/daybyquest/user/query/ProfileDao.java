package daybyquest.user.query;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProfileDao {

    Profile getByUsername(final Long userId, final String username);

    Profile getById(final Long userId, final Long targetId);

    Profile getMine(final Long userId);

    List<Profile> findAllByUserIdIn(final Long userId, final Collection<Long> targetIds);

    Map<Long, Profile> findMapByUserIdIn(final Long userId, final Collection<Long> targetIds);
}
