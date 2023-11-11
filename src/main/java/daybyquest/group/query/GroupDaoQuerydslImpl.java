package daybyquest.group.query;

import static daybyquest.group.domain.QGroup.group;
import static daybyquest.group.domain.QGroupUser.groupUser;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.domain.GroupUserRole;
import org.springframework.stereotype.Repository;

@Repository
public class GroupDaoQuerydslImpl implements GroupDao {

    private final JPAQueryFactory factory;

    public GroupDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public GroupData getById(final Long userId, final Long id) {
        final GroupData groupData = factory.select(Projections.constructor(GroupData.class,
                        group.id,
                        group.name,
                        group.description,
                        group.interest,
                        group.image,
                        JPAExpressions.select(groupUser.count())
                                .from(groupUser)
                                .where(groupUser.group.id.eq(id)),
                        JPAExpressions.selectFrom(groupUser)
                                .where(groupUser.userId.eq(userId), groupUser.group.id.eq(id),
                                        groupUser.role.eq(GroupUserRole.MANAGER))
                                .exists()
                )).from(group)
                .where(group.id.eq(id))
                .fetchOne();
        if (groupData == null) {
            throw new NotExistGroupException();
        }
        return groupData;
    }

    @Override
    public LongIdList findUserIdsByGroupId(final Long id, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(groupUser.userId)
                .from(groupUser)
                .where(groupUser.group.id.eq(id), greaterThanUserId(page.lastId()))
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression greaterThanUserId(final Long userId) {
        if (userId == null) {
            return null;
        }
        return groupUser.userId.gt(userId);
    }
}
