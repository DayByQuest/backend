package daybyquest.badge.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.badge.application.GetMyBadgesService;
import daybyquest.badge.dto.response.PageBadgesResponse;
import daybyquest.global.query.NoOffsetTimePage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BadgeQueryApi {

    private GetMyBadgesService getMyBadgesService;

    public BadgeQueryApi(final GetMyBadgesService getMyBadgesService) {
        this.getMyBadgesService = getMyBadgesService;
    }

    @GetMapping("/badge")
    @Authorization
    public ResponseEntity<PageBadgesResponse> getMyBadges(final AccessUser accessUser,
            final NoOffsetTimePage page) {
        final PageBadgesResponse response = getMyBadgesService.invoke(accessUser.getId(), page);
        return ResponseEntity.ok(response);
    }
}
