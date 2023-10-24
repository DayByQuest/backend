package daybyquest.relation.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.relation.application.GetFollowersService;
import daybyquest.relation.application.GetFollowingsService;
import daybyquest.user.dto.response.PageProfilesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowQueryApi {

    private final GetFollowersService getFollowersService;

    private final GetFollowingsService getFollowingsService;

    public FollowQueryApi(final GetFollowersService getFollowersService,
            final GetFollowingsService getFollowingsService) {
        this.getFollowersService = getFollowersService;
        this.getFollowingsService = getFollowingsService;
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
