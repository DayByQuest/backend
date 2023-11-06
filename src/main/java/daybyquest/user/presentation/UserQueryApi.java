package daybyquest.user.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.user.application.CheckUsernameService;
import daybyquest.user.application.GetMyProfileService;
import daybyquest.user.application.GetProfileByUsernameService;
import daybyquest.user.application.GetVisibilityService;
import daybyquest.user.dto.response.MyProfileResponse;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.dto.response.UserVisibilityResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryApi {

    private final GetProfileByUsernameService getProfileByUsernameService;

    private final GetMyProfileService getMyProfileService;

    private final CheckUsernameService checkUsernameService;

    private final GetVisibilityService getVisibilityService;

    public UserQueryApi(final GetProfileByUsernameService getProfileByUsernameService,
            final GetMyProfileService getMyProfileService, final CheckUsernameService checkUsernameService,
            final GetVisibilityService getVisibilityService) {
        this.getProfileByUsernameService = getProfileByUsernameService;
        this.getMyProfileService = getMyProfileService;
        this.checkUsernameService = checkUsernameService;
        this.getVisibilityService = getVisibilityService;
    }

    @GetMapping("/profile/{username}")
    @Authorization(required = false)
    public ResponseEntity<ProfileResponse> getProfileByUsername(final AccessUser accessUser,
            @PathVariable final String username) {
        final ProfileResponse response = getProfileByUsernameService.invoke(accessUser.getId(), username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @Authorization
    public ResponseEntity<MyProfileResponse> getMyProfile(final AccessUser accessUser) {
        final MyProfileResponse response = getMyProfileService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile/{username}/check")
    public ResponseEntity<Void> checkUsername(@PathVariable final String username) {
        checkUsernameService.invoke(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile/visibility")
    @Authorization
    public ResponseEntity<UserVisibilityResponse> getVisibility(final AccessUser accessUser) {
        final UserVisibilityResponse response = getVisibilityService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }
}
