package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.post.application.GetPostService;
import daybyquest.post.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostQueryApi {

    private final GetPostService getPostService;

    public PostQueryApi(final GetPostService getPostService) {
        this.getPostService = getPostService;
    }

    @GetMapping("/post/{postId}")
    @Authorization
    public ResponseEntity<PostResponse> getPost(@UserId final Long loginId, @PathVariable final Long postId) {
        final PostResponse response = getPostService.invoke(loginId, postId);
        return ResponseEntity.ok(response);
    }
}
