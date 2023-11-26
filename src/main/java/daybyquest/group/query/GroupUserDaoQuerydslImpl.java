package daybyquest.group.query;

import static daybyquest.group.domain.QGroupUser.groupUser;
import static daybyquest.post.domain.QPost.post;
import static daybyquest.relation.domain.QBlock.block;
import static daybyquest.relation.domain.QFollow.follow;
import static daybyquest.user.domain.QUser.user;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class GroupUserDaoQuerydslImpl implements GroupUserDao {

    private final JPAQueryFactory factory;

    public GroupUserDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public LongIdList findUserIdsByGroupId(final Long id, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(groupUser.userId)
                .from(groupUser)
                .where(groupUser.group.id.eq(id), gtUserId(page.lastId()))
                .orderBy(groupUser.userId.asc())
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression gtUserId(final Long userId) {
        if (userId == null) {
            return null;
        }
        return groupUser.userId.gt(userId);
    }

    @Override
    public List<GroupUserData> findAllByUserIdsIn(final Long userId, final Collection<Long> ids) {
        return factory
                .select(groupUserProjection(userId))
                .from(groupUser)
                .join(user).on(groupUser.userId.eq(user.id), user.id.in(ids))
                .orderBy(user.id.asc())
                .fetch();
    }

    private ConstructorExpression<GroupUserData> groupUserProjection(final Long userId) {
        return Projections.constructor(GroupUserData.class,
                user.id,
                user.username,
                user.name,
                user.image.identifier,
                JPAExpressions.select(post.count())
                        .from(post)
                        .where(post.userId.eq(user.id)),
                JPAExpressions.selectFrom(follow)
                        .where(follow.userId.eq(userId).and(follow.targetId.eq(user.id)))
                        .exists(),
                JPAExpressions.selectFrom(block)
                        .where(block.userId.eq(userId).and(block.targetId.eq(user.id)))
                        .exists(),
                groupUser.role
        );
    }
}
