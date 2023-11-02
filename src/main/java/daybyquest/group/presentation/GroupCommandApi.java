package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.group.application.SaveGroupService;
import daybyquest.group.dto.request.SaveGroupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GroupCommandApi {

    private final SaveGroupService saveGroupService;

    public GroupCommandApi(final SaveGroupService saveGroupService) {
        this.saveGroupService = saveGroupService;
    }

    @PostMapping("/group")
    @Authorization
    public ResponseEntity<Void> saveGroup(final AccessUser accessUser,
            @RequestPart("image") final MultipartFile file,
            @RequestPart("request") @Valid final SaveGroupRequest request) {
        saveGroupService.invoke(accessUser.getId(), request, file);
        return ResponseEntity.ok().build();
    }
}
