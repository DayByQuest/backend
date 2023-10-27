package daybyquest.like.domain;

import org.springframework.data.repository.Repository;

interface PostDislikeRepository extends Repository<PostDislike, PostDislikeId> {

    PostDislike save(PostDislike postDislike);

    boolean existsByUserIdAndPostId(final Long userId, final Long postId);

    void deleteByUserIdAndPostId(final Long userId, final Long postId);

}
