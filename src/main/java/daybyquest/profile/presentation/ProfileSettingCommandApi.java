package daybyquest.profile.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.profile.application.SaveBadgeListService;
import daybyquest.profile.dto.request.SaveBadgeListRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileSettingCommandApi {

    private final SaveBadgeListService saveBadgeListService;

    public ProfileSettingCommandApi(final SaveBadgeListService saveBadgeListService) {
        this.saveBadgeListService = saveBadgeListService;
    }

    @PatchMapping("/badge")
    @Authorization
    public ResponseEntity<Void> saveBadgeList(final AccessUser accessUser,
            @RequestBody @Valid final SaveBadgeListRequest request) {
        saveBadgeListService.invoke(accessUser.getId(), request);
        return ResponseEntity.ok().build();
    }
}
