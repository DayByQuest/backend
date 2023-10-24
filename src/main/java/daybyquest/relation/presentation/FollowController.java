package daybyquest.relation.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.relation.application.DeleteFollowService;
import daybyquest.relation.application.GetFollowersService;
import daybyquest.relation.application.GetFollowingsService;
import daybyquest.relation.application.SaveFollowService;
import daybyquest.user.dto.response.PageProfilesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {

    private final SaveFollowService saveFollowService;

    private final DeleteFollowService deleteFollowService;

    private final GetFollowersService getFollowersService;

    private final GetFollowingsService getFollowingsService;

    public FollowController(final SaveFollowService saveFollowService,
            final DeleteFollowService deleteFollowService, final GetFollowersService getFollowersService,
            final GetFollowingsService getFollowingsService) {
        this.saveFollowService = saveFollowService;
        this.deleteFollowService = deleteFollowService;
        this.getFollowersService = getFollowersService;
        this.getFollowingsService = getFollowingsService;
    }

    @PostMapping("/profile/{username}/follow")
    @Authorization
    public ResponseEntity<Void> saveFollow(@UserId final Long loginId,
            @PathVariable final String username) {
        saveFollowService.invoke(loginId, username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/profile/{username}/follow")
    @Authorization
    public ResponseEntity<Void> deleteFollow(@UserId final Long loginId,
            @PathVariable final String username) {
        deleteFollowService.invoke(loginId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/followings")
    @Authorization
    public ResponseEntity<PageProfilesResponse> getFollowings(@UserId final Long loginId,
            final NoOffsetIdPage page) {
        final PageProfilesResponse response = getFollowingsService.invoke(loginId, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/followers")
    @Authorization
    public ResponseEntity<PageProfilesResponse> getFollowers(@UserId final Long loginId,
            final NoOffsetIdPage page) {
        final PageProfilesResponse response = getFollowersService.invoke(loginId, page);
        return ResponseEntity.ok(response);
    }
}
