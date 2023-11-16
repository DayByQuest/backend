package daybyquest.user.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProfileDao {

    Profile getByUsername(final Long userId, final String username);

    Profile getById(final Long userId, final Long targetId);

    Profile getMine(final Long userId);

    LongIdList findIdsByUsernameLike(final String keyword, final NoOffsetIdPage page);

    List<Profile> findAllByUserIdIn(final Long userId, final Collection<Long> targetIds);

    Map<Long, Profile> findMapByUserIdIn(final Long userId, final Collection<Long> targetIds);
}
