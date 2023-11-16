package daybyquest.post.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

interface PostRepository extends Repository<Post, Long> {

    Post save(final Post post);

    boolean existsById(final Long id);

    Optional<Post> findById(final Long id);

    List<Post> findTop10ByQuestIdAndState(final Long questId, final PostState state);
}