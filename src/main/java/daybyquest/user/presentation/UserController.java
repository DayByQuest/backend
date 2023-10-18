package daybyquest.user.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.user.application.SaveUserService;
import daybyquest.user.application.UpdateUserService;
import daybyquest.user.dto.request.SaveUserRequest;
import daybyquest.user.dto.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final SaveUserService saveUserService;

    private final UpdateUserService updateUserService;

    public UserController(final SaveUserService saveUserService, final UpdateUserService updateUserService) {
        this.saveUserService = saveUserService;
        this.updateUserService = updateUserService;
    }

    @PostMapping("/profile")
    public ResponseEntity<Long> saveUser(@RequestBody @Valid final SaveUserRequest request) {
        final Long userId = saveUserService.invoke(request);
        return ResponseEntity.ok(userId);
    }

    @PatchMapping("/profile")
    @Authorization
    public ResponseEntity<Void> updateUser(@UserId final Long loginId,
        @RequestBody @Valid final UpdateUserRequest request) {
        updateUserService.invoke(loginId, request);
        return ResponseEntity.ok().build();
    }
}
