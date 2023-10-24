package daybyquest.relation.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.relation.application.DeleteFollowService;
import daybyquest.relation.application.DeleteFollowerService;
import daybyquest.relation.application.SaveFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowCommandApi {

    private final SaveFollowService saveFollowService;

    private final DeleteFollowService deleteFollowService;

    private final DeleteFollowerService deleteFollowerService;

    public FollowCommandApi(final SaveFollowService saveFollowService,
            final DeleteFollowService deleteFollowService,
            final DeleteFollowerService deleteFollowerService) {
        this.saveFollowService = saveFollowService;
        this.deleteFollowService = deleteFollowService;
        this.deleteFollowerService = deleteFollowerService;
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

    @DeleteMapping("/profile/{username}/follower")
    @Authorization
    public ResponseEntity<Void> deleteFollower(@UserId final Long loginId,
            @PathVariable final String username) {
        deleteFollowerService.invoke(loginId, username);
        return ResponseEntity.ok().build();
    }
}
