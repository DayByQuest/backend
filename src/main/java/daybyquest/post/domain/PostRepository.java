package daybyquest.post.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface PostRepository extends Repository<Post, Long> {

    Post save(Post post);

    Optional<Post> findById(Long id);

}