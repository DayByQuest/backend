package daybyquest.post.query;

import static daybyquest.image.vo.QImage.image;
import static daybyquest.like.domain.QPostLike.postLike;
import static daybyquest.post.domain.QPost.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistPostException;
import daybyquest.image.vo.Image;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostDaoQuerydslImpl implements PostDao {

    private final JPAQueryFactory factory;

    public PostDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public PostData getByPostId(final Long userId, final Long postId) {
        final PostData postData = factory.select(Projections.constructor(PostData.class,
                        post.userId,
                        post.id,
                        post.content,
                        post.updatedAt,
                        JPAExpressions.selectFrom(postLike)
                                .where(postLike.userId.eq(userId).and(postLike.postId.eq(postId)))
                                .exists()))
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
}
