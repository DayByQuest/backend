package daybyquest.comment.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.comment.application.GetCommentsByPostIdService;
import daybyquest.comment.dto.response.PageCommentsResponse;
import daybyquest.global.query.NoOffsetIdPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentQueryApi {

    private final GetCommentsByPostIdService getCommentsByPostIdService;

    public CommentQueryApi(final GetCommentsByPostIdService getCommentsByPostIdService) {
        this.getCommentsByPostIdService = getCommentsByPostIdService;
    }

    @GetMapping("/comment/{postId}")
    @Authorization
    public ResponseEntity<PageCommentsResponse> getCommentsByPostId(final AccessUser accessUser,
            @PathVariable final Long postId, final NoOffsetIdPage page) {
        final PageCommentsResponse response = getCommentsByPostIdService.invoke(accessUser.getId(), postId,
                page);
        return ResponseEntity.ok(response);
    }
}
