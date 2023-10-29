package daybyquest.comment.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface CommentRepository extends Repository<Comment, Long> {

    Comment save(final Comment comment);

    Optional<Comment> findById(final Long id);

}