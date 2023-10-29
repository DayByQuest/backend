package daybyquest.comment.query;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;

public interface CommentDao {

    LongIdList findIdsByPostId(final Long userId, final Long postId, final NoOffsetIdPage page);

    List<CommentData> findAllByIdIn(final Long userId, final Collection<Long> commentIds);
}
