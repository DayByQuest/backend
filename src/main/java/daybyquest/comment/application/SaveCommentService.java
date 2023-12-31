package daybyquest.comment.application;

import daybyquest.comment.domain.Comment;
import daybyquest.comment.domain.Comments;
import daybyquest.comment.dto.request.SaveCommentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveCommentService {

    private final Comments comments;

    public SaveCommentService(final Comments comments) {
        this.comments = comments;
    }

    @Transactional
    public Long invoke(final Long loginId, final Long postId, final SaveCommentRequest request) {
        final Comment comment = new Comment(loginId, postId, request.getContent());
        return comments.save(comment).getId();
    }
}
