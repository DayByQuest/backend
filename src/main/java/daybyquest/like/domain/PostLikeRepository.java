package daybyquest.like.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface PostLikeRepository extends Repository<PostLike, PostLikeId> {

    PostLike save(PostLike postLike);

    Optional<PostLike> findByUserIdAndPostId(Long userId, Long postId);

}
