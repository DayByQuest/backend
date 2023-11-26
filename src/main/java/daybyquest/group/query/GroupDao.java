package daybyquest.group.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;

public interface GroupDao {

    GroupData getById(final Long userId, final Long id);

    LongIdList findUserIdsByGroupId(final Long id, final NoOffsetIdPage page);

    List<GroupData> findAllByUserId(final Long userId);

    List<GroupData> findAllByIdsIn(final Long userId, final Collection<Long> ids);

    LongIdList findIdsByNameLike(final String keyword, final NoOffsetIdPage page);

    LongIdList findIdsByInterest(final String interest, final NoOffsetIdPage page);
}
