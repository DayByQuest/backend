package daybyquest.user.presentation;

import daybyquest.auth.UserId;
import daybyquest.user.application.GetProfileByUsernameService;
import daybyquest.user.dto.response.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    private final GetProfileByUsernameService getProfileByUsernameService;

    public ProfileController(final GetProfileByUsernameService getProfileByUsernameService) {
        this.getProfileByUsernameService = getProfileByUsernameService;
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ProfileResponse> getProfileByUsername(@UserId final Long loginId,
        @PathVariable final String username) {
        final ProfileResponse response = getProfileByUsernameService.invoke(loginId, username);
        return ResponseEntity.ok(response);
    }

}
