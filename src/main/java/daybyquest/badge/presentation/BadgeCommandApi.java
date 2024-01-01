package daybyquest.badge.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.badge.application.SaveBadgeService;
import daybyquest.badge.dto.request.SaveBadgeRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class BadgeCommandApi {

    private final SaveBadgeService saveBadgeService;

    public BadgeCommandApi(final SaveBadgeService saveBadgeService) {
        this.saveBadgeService = saveBadgeService;
    }

    @PostMapping("/badge")
    @Authorization(admin = true)
    public ResponseEntity<Void> saveBadge(final AccessUser user,
            @RequestPart("image") final MultipartFile image,
            @RequestPart("request") @Valid final SaveBadgeRequest request) {
        saveBadgeService.invoke(request, image);
        return ResponseEntity.ok().build();
    }
}
