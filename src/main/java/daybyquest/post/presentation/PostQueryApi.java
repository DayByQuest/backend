package daybyquest.post.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.application.GetPostByUsernameService;
import daybyquest.post.application.GetPostFromFollowingService;
import daybyquest.post.application.GetPostService;
import daybyquest.post.application.GetTrackerService;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.dto.response.PostWithQuestResponse;
import daybyquest.post.dto.response.TrackerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostQueryApi {

    private final GetPostService getPostService;

    private final GetPostFromFollowingService getPostFromFollowingService;

    private final GetPostByUsernameService getPostByUsernameService;

    private final GetTrackerService getTrackerService;

    public PostQueryApi(final GetPostService getPostService,
            final GetPostFromFollowingService getPostFromFollowingService,
            final GetPostByUsernameService getPostByUsernameService,
            final GetTrackerService getTrackerService) {
        this.getPostService = getPostService;
        this.getPostFromFollowingService = getPostFromFollowingService;
        this.getPostByUsernameService = getPostByUsernameService;
        this.getTrackerService = getTrackerService;
    }

    @GetMapping("/post/{postId}")
    @Authorization
    public ResponseEntity<PostWithQuestResponse> getPost(final AccessUser accessUser,
            @PathVariable final Long postId) {
        final PostWithQuestResponse response = getPostService.invoke(accessUser.getId(), postId);
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

    @GetMapping("/profile/{username}/tracker")
    @Authorization
    public ResponseEntity<TrackerResponse> getTracker(final AccessUser accessUser,
            @PathVariable final String username) {
        final TrackerResponse response = getTrackerService.invoke(accessUser.getId(), username);
        return ResponseEntity.ok(response);
    }
}
