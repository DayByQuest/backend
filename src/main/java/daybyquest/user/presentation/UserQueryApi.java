package daybyquest.user.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.user.application.GetMyProfileService;
import daybyquest.user.application.GetProfileByUsernameService;
import daybyquest.user.dto.response.MyProfileResponse;
import daybyquest.user.dto.response.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserQueryApi {

    private final GetProfileByUsernameService getProfileByUsernameService;

    private final GetMyProfileService getMyProfileService;

    public UserQueryApi(final GetProfileByUsernameService getProfileByUsernameService,
            final GetMyProfileService getMyProfileService) {
        this.getProfileByUsernameService = getProfileByUsernameService;
        this.getMyProfileService = getMyProfileService;
    }

    @GetMapping("/profile/{username}")
    @Authorization(required = false)
    public ResponseEntity<ProfileResponse> getProfileByUsername(@UserId final Long loginId,
            @PathVariable final String username) {
        final ProfileResponse response = getProfileByUsernameService.invoke(loginId, username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    @Authorization(required = false)
    public ResponseEntity<MyProfileResponse> getMyProfile(@UserId final Long loginId) {
        final MyProfileResponse response = getMyProfileService.invoke(loginId);
        return ResponseEntity.ok(response);
    }
}
