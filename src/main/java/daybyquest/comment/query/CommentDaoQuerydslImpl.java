package daybyquest.comment.query;

import static daybyquest.comment.domain.QComment.comment;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoQuerydslImpl implements CommentDao {

    private final JPAQueryFactory factory;

    public CommentDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public LongIdList findIdsByPostId(final Long userId, final Long postId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(comment.id)
                .from(comment)
                .where(comment.postId.eq(postId), gtCommentId(page.lastId()))
                .orderBy(comment.id.asc())
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression gtCommentId(final Long commentId) {
        return commentId == null ? null : comment.id.gt(commentId);
    }

    @Override
    public List<CommentData> findAllByIdIn(final Long userId, final Collection<Long> commentIds) {
        return factory.select(Projections.constructor(CommentData.class,
                        comment.userId,
                        comment.id,
                        comment.content,
                        comment.updatedAt))
                .from(comment)
                .where(comment.id.in(commentIds))
                .fetch();
    }
}
