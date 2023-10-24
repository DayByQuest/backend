package daybyquest.user.query;

import static daybyquest.post.domain.QPost.post;
import static daybyquest.relation.domain.QBlock.block;
import static daybyquest.relation.domain.QFollow.follow;
import static daybyquest.user.domain.QUser.user;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistUserException;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDaoQuerydslImpl implements ProfileDao {

    private final JPAQueryFactory factory;

    public ProfileDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public Profile getByUsername(final Long userId, final String username) {
        final Profile profile = factory
                .select(profileProjection(userId))
                .from(user)
                .where(user.username.eq(username))
                .fetchOne();
        if (profile == null) {
            throw new NotExistUserException();
        }
        return profile;
    }

    private ConstructorExpression<Profile> profileProjection(final Long userId) {
        return Projections.constructor(Profile.class,
                user.id,
                user.username,
                user.name,
                user.image.imageIdentifier,
                JPAExpressions.select(post.count())
                        .from(post)
                        .where(post.userId.eq(userId)),
                JPAExpressions.selectFrom(follow)
                        .where(follow.userId.eq(userId).and(follow.targetId.eq(user.id)))
                        .exists(),
                JPAExpressions.selectFrom(block)
                        .where(block.userId.eq(userId).and(block.targetId.eq(user.id)))
                        .exists()
        );
    }

    @Override
    public Profile getMine(final Long userId) {
        final Profile profile = factory
                .select(Projections.constructor(Profile.class,
                        user.id,
                        user.username,
                        user.name,
                        user.image.imageIdentifier,
                        JPAExpressions.select(post.count())
                                .from(post)
                                .where(post.userId.eq(userId)),
                        JPAExpressions.select(follow.count())
                                .from(follow)
                                .where(follow.userId.eq(userId)),
                        JPAExpressions.select(follow.count())
                                .from(follow)
                                .where(follow.targetId.eq(userId))
                ))
                .from(user)
                .where(user.id.eq(userId))
                .fetchOne();
        if (profile == null) {
            throw new NotExistUserException();
        }
        return profile;
    }

    @Override
    public List<Profile> findAllByUserIdIn(final Long userId, final Collection<Long> targetIds) {
        return factory
                .select(profileProjection(userId))
                .from(user)
                .where(user.id.in(targetIds))
                .fetch();
    }
}
