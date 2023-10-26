package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.application.GetPostFromFollowingService;
import daybyquest.post.application.GetPostService;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.dto.response.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostQueryApi {

    private final GetPostService getPostService;

    private final GetPostFromFollowingService getPostFromFollowingService;

    public PostQueryApi(final GetPostService getPostService,
            final GetPostFromFollowingService getPostFromFollowingService) {
        this.getPostService = getPostService;
        this.getPostFromFollowingService = getPostFromFollowingService;
    }

    @GetMapping("/post/{postId}")
    @Authorization
    public ResponseEntity<PostResponse> getPost(@UserId final Long loginId, @PathVariable final Long postId) {
        final PostResponse response = getPostService.invoke(loginId, postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    @Authorization
    public ResponseEntity<PagePostsResponse> getPost(@UserId final Long loginId, final NoOffsetIdPage page) {
        final PagePostsResponse response = getPostFromFollowingService.invoke(loginId, page);
        return ResponseEntity.ok(response);
    }
}
