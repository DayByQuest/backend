package daybyquest.post.query;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static daybyquest.image.domain.QImage.image;
import static daybyquest.like.domain.QPostLike.postLike;
import static daybyquest.post.domain.PostState.SUCCESS;
import static daybyquest.post.domain.QPost.post;
import static daybyquest.quest.domain.QQuest.quest;
import static daybyquest.relation.domain.QFollow.follow;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistPostException;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.image.domain.Image;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoQuerydslImpl implements PostDao {

    private final JPAQueryFactory factory;

    public PostDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public PostData getByPostId(final Long userId, final Long postId) {
        final PostData postData = factory.select(postDataProjection(userId))
                .from(post)
                .where(post.id.eq(postId))
                .fetchOne();
        if (postData == null) {
            throw new NotExistPostException();
        }
        final List<Image> images = factory.select(image)
                .from(post)
                .leftJoin(post.images, image)
                .where(post.id.eq(postId))
                .fetch();
        postData.setImages(images);
        return postData;
    }

    private ConstructorExpression<PostData> postDataProjection(final Long userId) {
        return Projections.constructor(PostData.class,
                post.userId,
                post.id,
                post.content,
                post.updatedAt,
                JPAExpressions.selectFrom(postLike)
                        .where(postLike.userId.eq(userId).and(postLike.postId.eq(post.id)))
                        .exists(),
                post.questId,
                JPAExpressions.select(quest.title)
                        .from(quest)
                        .where(quest.id.eq(post.questId)),
                post.state);
    }

    @Override
    public LongIdList findPostIdsFromFollowings(final Long userId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(post.id)
                .from(post)
                .where(post.userId.in(JPAExpressions.select(follow.targetId)
                                .from(follow)
                                .where(follow.userId.eq(userId)))
                        , ltPostId(page.lastId())
                )
                .orderBy(post.id.desc())
                .limit(page.limit())
                .fetch());
    }

    private BooleanExpression ltPostId(final Long postId) {
        return postId == null ? null : post.id.lt(postId);
    }

    @Override
    public LongIdList findPostIdsByUserId(final Long userId, final Long targetId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(post.id)
                .from(post)
                .where(post.userId.eq(targetId)
                        , ltPostId(page.lastId())
                )
                .orderBy(post.id.desc())
                .limit(page.limit())
                .fetch());
    }

    @Override
    public LongIdList findPostIdsByQuestId(final Long userId, final Long questId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(post.id)
                .from(post)
                .where(post.questId.eq(questId)
                        , ltPostId(page.lastId())
                )
                .orderBy(post.id.desc())
                .limit(page.limit())
                .fetch());
    }

    @Override
    public LongIdList findPostIdsByGroupId(final Long userId, final Long groupId, final NoOffsetIdPage page) {
        return new LongIdList(factory.select(post.id)
                .from(post)
                .join(quest).on(quest.groupId.eq(groupId), quest.id.eq(post.questId))
                .where(ltPostId(page.lastId()))
                .orderBy(post.id.desc())
                .limit(page.limit())
                .fetch());
    }

    @Override
    public List<PostData> findAllByIdIn(final Long userId, final Collection<Long> postIds) {
        final Map<Long, PostData> postDataMap = factory.from(post)
                .where(post.id.in(postIds))
                .transform(groupBy(post.id).as(postDataProjection(userId)));
        final Map<Long, List<Image>> imageMap = factory.from(post)
                .leftJoin(post.images, image)
                .where(post.id.in(postIds))
                .transform(groupBy(post.id).as(list(image)));
        postDataMap.forEach((id, postData) -> postData.setImages(imageMap.get(id)));
        return postDataMap.values().stream().toList();
    }

    @Override
    public List<SimplePostData> findAllBySuccessAndUploadedAtAfter(final Long userId,
            final LocalDateTime time) {
        return factory.select(Projections.constructor(SimplePostData.class,
                        post.id,
                        post.uploadedAt))
                .from(post)
                .where(post.userId.eq(userId), post.uploadedAt.after(time), post.state.eq(SUCCESS))
                .fetch();
    }
}
