package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.group.application.GetGroupProfileService;
import daybyquest.group.dto.response.GroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupQueryApi {

    private final GetGroupProfileService getGroupProfileService;

    public GroupQueryApi(final GetGroupProfileService getGroupProfileService) {
        this.getGroupProfileService = getGroupProfileService;
    }

    @GetMapping("/group/{groupId}")
    @Authorization
    public ResponseEntity<GroupResponse> getGroupProfile(final AccessUser accessUser,
            @PathVariable final Long groupId) {
        final GroupResponse response = getGroupProfileService.invoke(accessUser.getId(), groupId);
        return ResponseEntity.ok(response);
    }
}
