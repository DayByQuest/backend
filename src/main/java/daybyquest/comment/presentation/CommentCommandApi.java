package daybyquest.comment.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.comment.application.SaveCommentService;
import daybyquest.comment.dto.request.SaveCommentRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentCommandApi {

    private final SaveCommentService saveCommentService;

    public CommentCommandApi(final SaveCommentService saveCommentService) {
        this.saveCommentService = saveCommentService;
    }

    @PostMapping("/comment/{postId}")
    @Authorization
    public ResponseEntity<Void> saveComment(final AccessUser accessUser, @PathVariable final Long postId,
            @RequestBody @Valid final SaveCommentRequest request) {
        saveCommentService.invoke(accessUser.getId(), postId, request);
        return ResponseEntity.ok().build();
    }
}
