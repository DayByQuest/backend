package daybyquest.badge.query;

import daybyquest.global.query.NoOffsetTimePage;
import java.util.Collection;
import java.util.List;

public interface BadgeDao {

    List<BadgeData> getBadgePageByUserIds(final Long userId, final NoOffsetTimePage page);

    List<BadgeData> findAllByIdIn(final Collection<Long> ids);
}
