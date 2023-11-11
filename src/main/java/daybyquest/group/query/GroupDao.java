package daybyquest.group.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;

public interface GroupDao {

    GroupData getById(final Long userId, final Long id);

    LongIdList findUserIdsByGroupId(final Long id, final NoOffsetIdPage page);
}
