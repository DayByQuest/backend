package daybyquest.profile.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.badge.dto.response.MultipleBadgesResponse;
import daybyquest.profile.application.GetPresetBadgeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileQueryApi {

    private final GetPresetBadgeService getPresetBadgeService;

    public ProfileQueryApi(final GetPresetBadgeService getPresetBadgeService) {
        this.getPresetBadgeService = getPresetBadgeService;
    }

    @GetMapping("/badge/{username}")
    @Authorization
    public ResponseEntity<MultipleBadgesResponse> getBadges(final AccessUser user,
            @PathVariable final String username) {
        final MultipleBadgesResponse response = getPresetBadgeService.invoke(username);
        return ResponseEntity.ok(response);
    }
}
