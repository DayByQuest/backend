package daybyquest.badge.query;

import static daybyquest.badge.domain.QBadge.badge;
import static daybyquest.badge.domain.QOwning.owning;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.query.NoOffsetTimePage;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BadgeDaoQuerydslImpl implements BadgeDao {

    private final JPAQueryFactory factory;

    public BadgeDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<BadgeData> getBadgePageByUserIds(final Long userId, final NoOffsetTimePage page) {
        return factory.select(projectBadgeData())
                .from(owning)
                .innerJoin(owning.badge, badge)
                .where(owning.userId.eq(userId), ltAcquiredAt(page.lastTime()))
                .orderBy(owning.acquiredAt.desc())
                .limit(page.limit())
                .fetch();
    }

    private static ConstructorExpression<BadgeData> projectBadgeData() {
        return Projections.constructor(BadgeData.class,
                badge.id,
                badge.name,
                badge.image,
                owning.acquiredAt);
    }

    private BooleanExpression ltAcquiredAt(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return owning.acquiredAt.lt(localDateTime);
    }

    @Override
    public List<BadgeData> findAllByIdIn(final Collection<Long> ids) {
        return factory.select(projectBadgeData())
                .from(owning)
                .innerJoin(owning.badge, badge)
                .where(badge.id.in(ids))
                .fetch();
    }
}
