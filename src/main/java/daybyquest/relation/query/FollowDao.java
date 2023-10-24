package daybyquest.relation.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;

public interface FollowDao {

    LongIdList getFollowingIds(final Long userId, final NoOffsetIdPage page);

    LongIdList getFollowerIds(final Long targetId, final NoOffsetIdPage page);
}
