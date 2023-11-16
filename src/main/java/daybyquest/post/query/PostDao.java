package daybyquest.post.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PostDao {

    PostData getByPostId(final Long userId, final Long postId);

    LongIdList findPostIdsFromFollowings(final Long userId, final NoOffsetIdPage page);

    LongIdList findPostIdsByUserId(final Long userId, final Long targetId, final NoOffsetIdPage page);

    LongIdList findPostIdsByQuestId(final Long userId, final Long questId, final NoOffsetIdPage page);

    LongIdList findPostIdsByGroupId(final Long userId, final Long groupId, final NoOffsetIdPage page);

    List<PostData> findAllByIdIn(final Long userId, final Collection<Long> postIds);

    List<SimplePostData> findAllBySuccessAndUploadedAtAfter(final Long userId, final LocalDateTime time);
}
