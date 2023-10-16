package daybyquest.like.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface PostDislikeRepository extends Repository<PostDislike, PostDislikeId> {

    PostDislike save(PostDislike postDislike);

    Optional<PostDislike> findByUserIdAndPostId(Long userId, Long postId);

}
