package daybyquest.like.domain;

import org.springframework.data.repository.Repository;

interface PostLikeRepository extends Repository<PostLike, PostLikeId> {

    PostLike save(final PostLike postLike);

    boolean existsByUserIdAndPostId(final Long userId, final Long postId);

    void deleteByUserIdAndPostId(final Long userId, final Long postId);

}
