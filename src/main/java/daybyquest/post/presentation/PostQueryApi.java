package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.application.GetPostByUsernameService;
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

    private final GetPostByUsernameService getPostByUsernameService;

    public PostQueryApi(final GetPostService getPostService,
            final GetPostFromFollowingService getPostFromFollowingService,
            final GetPostByUsernameService getPostByUsernameService) {
        this.getPostService = getPostService;
        this.getPostFromFollowingService = getPostFromFollowingService;
        this.getPostByUsernameService = getPostByUsernameService;
    }

    @GetMapping("/post/{postId}")
    @Authorization
    public ResponseEntity<PostResponse> getPost(final AccessUser accessUser,
            @PathVariable final Long postId) {
        final PostResponse response = getPostService.invoke(accessUser.getId(), postId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/feed")
    @Authorization
    public ResponseEntity<PagePostsResponse> getPostFromFollowings(final AccessUser accessUser,
            final NoOffsetIdPage page) {
        final PagePostsResponse response = getPostFromFollowingService.invoke(accessUser.getId(), page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{username}/post")
    @Authorization
    public ResponseEntity<PagePostsResponse> getPostByUsername(final AccessUser accessUser,
            @PathVariable final String username, final NoOffsetIdPage page) {
        final PagePostsResponse response = getPostByUsernameService.invoke(accessUser.getId(), username,
                page);
        return ResponseEntity.ok(response);
    }
}
