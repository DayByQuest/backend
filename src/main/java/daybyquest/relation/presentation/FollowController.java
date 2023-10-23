package daybyquest.relation.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.relation.application.SaveFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {

    private final SaveFollowService saveFollowService;

    public FollowController(final SaveFollowService saveFollowService) {
        this.saveFollowService = saveFollowService;
    }

    @PostMapping("/profile/{username}/follow")
    @Authorization
    public ResponseEntity<Void> saveFollow(@UserId final Long loginId,
            @PathVariable final String username) {
        saveFollowService.invoke(loginId, username);
        return ResponseEntity.ok().build();
    }
}
