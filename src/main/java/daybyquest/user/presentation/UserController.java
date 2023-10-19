package daybyquest.user.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.UserId;
import daybyquest.user.application.SaveUserService;
import daybyquest.user.application.UpdateUserInterestService;
import daybyquest.user.application.UpdateUserService;
import daybyquest.user.application.UpdateVisibilityService;
import daybyquest.user.dto.request.SaveUserRequest;
import daybyquest.user.dto.request.UpdateUserInterestRequest;
import daybyquest.user.dto.request.UpdateUserRequest;
import daybyquest.user.dto.request.UpdateUserVisibilityRequest;
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

    private final UpdateVisibilityService updateVisibilityService;

    private final UpdateUserInterestService updateUserInterestService;

    public UserController(final SaveUserService saveUserService, final UpdateUserService updateUserService,
        final UpdateVisibilityService updateVisibilityService,
        final UpdateUserInterestService updateUserInterestService) {
        this.saveUserService = saveUserService;
        this.updateUserService = updateUserService;
        this.updateVisibilityService = updateVisibilityService;
        this.updateUserInterestService = updateUserInterestService;
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

    @PatchMapping("/profile/visibility")
    @Authorization
    public ResponseEntity<Void> updateUserVisibility(@UserId final Long loginId,
        @RequestBody @Valid final UpdateUserVisibilityRequest request) {
        updateVisibilityService.invoke(loginId, request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/profile/interest")
    @Authorization
    public ResponseEntity<Void> updateUserInterests(@UserId final Long loginId,
        @RequestBody @Valid final UpdateUserInterestRequest request) {
        updateUserInterestService.invoke(loginId, request);
        return ResponseEntity.ok().build();
    }
}
