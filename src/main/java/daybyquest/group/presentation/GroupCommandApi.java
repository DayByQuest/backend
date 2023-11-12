package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.group.application.DeleteMemberService;
import daybyquest.group.application.SaveGroupService;
import daybyquest.group.application.SaveMemberService;
import daybyquest.group.dto.request.SaveGroupRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GroupCommandApi {

    private final SaveGroupService saveGroupService;

    private final SaveMemberService saveMemberService;

    private final DeleteMemberService deleteMemberService;

    public GroupCommandApi(final SaveGroupService saveGroupService, final SaveMemberService saveMemberService,
            final DeleteMemberService deleteMemberService) {
        this.saveGroupService = saveGroupService;
        this.saveMemberService = saveMemberService;
        this.deleteMemberService = deleteMemberService;
    }

    @PostMapping("/group")
    @Authorization
    public ResponseEntity<Void> saveGroup(final AccessUser accessUser,
            @RequestPart("image") final MultipartFile file,
            @RequestPart("request") @Valid final SaveGroupRequest request) {
        saveGroupService.invoke(accessUser.getId(), request, file);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/group/{groupId}/user")
    @Authorization
    public ResponseEntity<Void> saveMember(final AccessUser accessUser, @PathVariable final Long groupId) {
        saveMemberService.invoke(accessUser.getId(), groupId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/group/{groupId}/user")
    @Authorization
    public ResponseEntity<Void> deleteMember(final AccessUser accessUser, @PathVariable final Long groupId) {
        deleteMemberService.invoke(accessUser.getId(), groupId);
        return ResponseEntity.ok().build();
    }
}
