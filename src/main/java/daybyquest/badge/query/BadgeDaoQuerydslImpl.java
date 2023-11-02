package daybyquest.badge.query;

import static daybyquest.badge.domain.QBadge.badge;
import static daybyquest.badge.domain.QOwning.owning;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.query.NoOffsetTimePage;
import java.time.LocalDateTime;
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
        return factory.select(Projections.constructor(BadgeData.class,
                        badge.id,
                        badge.name,
                        badge.image,
                        owning.acquiredAt))
                .from(owning)
                .innerJoin(owning.badge, badge)
                .where(owning.userId.eq(userId), ltAcquiredAt(page.lastTime()))
                .orderBy(owning.acquiredAt.desc())
                .limit(page.limit())
                .fetch();
    }

    private BooleanExpression ltAcquiredAt(final LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return owning.acquiredAt.lt(localDateTime);
    }
}
