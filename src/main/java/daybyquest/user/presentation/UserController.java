package daybyquest.user.presentation;

import daybyquest.user.application.SaveUserService;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final SaveUserService saveUserService;

    public UserController(final SaveUserService saveUserService) {
        this.saveUserService = saveUserService;
    }

    @PostMapping("/profile")
    public ResponseEntity<Long> getProfileByUsername(@RequestBody final SaveUserRequest request) {
        final Long userId = saveUserService.invoke(request);
        return ResponseEntity.ok(userId);
    }
}
