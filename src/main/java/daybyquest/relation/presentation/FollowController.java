package daybyquest.relation.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.relation.application.DeleteFollowService;
import daybyquest.relation.application.SaveFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowController {

    private final SaveFollowService saveFollowService;

    private final DeleteFollowService deleteFollowService;

    public FollowController(final SaveFollowService saveFollowService,
            final DeleteFollowService deleteFollowService) {
        this.saveFollowService = saveFollowService;
        this.deleteFollowService = deleteFollowService;
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
}
