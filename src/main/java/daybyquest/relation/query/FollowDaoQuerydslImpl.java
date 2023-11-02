package daybyquest.relation.query;

import static daybyquest.relation.domain.QFollow.follow;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import org.springframework.stereotype.Repository;

@Repository
public class FollowDaoQuerydslImpl implements FollowDao {

    private final JPAQueryFactory factory;

    public FollowDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public LongIdList getFollowingIds(final Long userId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(follow.targetId)
                .from(follow)
                .where(follow.userId.eq(userId), isTargetIdGtLastId(page.lastId()))
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression isTargetIdGtLastId(final Long lastId) {
        return lastId == null ? null : follow.targetId.gt(lastId);
    }

    @Override
    public LongIdList getFollowerIds(final Long targetId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(follow.userId)
                .from(follow)
                .where(follow.targetId.eq(targetId), isUserIdGtLastId(page.lastId()))
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression isUserIdGtLastId(final Long lastId) {
        return lastId == null ? null : follow.userId.gt(lastId);
    }
}
