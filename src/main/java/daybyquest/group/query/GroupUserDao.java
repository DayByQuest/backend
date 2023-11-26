package daybyquest.group.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;

public interface GroupUserDao {

    LongIdList findUserIdsByGroupId(final Long id, final NoOffsetIdPage page);

    List<GroupUserData> findAllByUserIdsIn(final Long userId, final Long groupId, final Collection<Long> ids);
}
