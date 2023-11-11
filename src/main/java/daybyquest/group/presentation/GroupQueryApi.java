package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.application.GetGroupProfileService;
import daybyquest.group.application.GetGroupUsersService;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.user.dto.response.PageProfilesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupQueryApi {

    private final GetGroupProfileService getGroupProfileService;

    private final GetGroupUsersService getGroupUsersService;

    public GroupQueryApi(final GetGroupProfileService getGroupProfileService,
            final GetGroupUsersService getGroupUsersService) {
        this.getGroupProfileService = getGroupProfileService;
        this.getGroupUsersService = getGroupUsersService;
    }

    @GetMapping("/group/{groupId}")
    @Authorization
    public ResponseEntity<GroupResponse> getGroupProfile(final AccessUser accessUser,
            @PathVariable final Long groupId) {
        final GroupResponse response = getGroupProfileService.invoke(accessUser.getId(), groupId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group/{groupId}/user")
    @Authorization
    public ResponseEntity<PageProfilesResponse> getGroupUsers(final AccessUser accessUser,
            @PathVariable final Long groupId, final NoOffsetIdPage page) {
        final PageProfilesResponse response = getGroupUsersService.invoke(accessUser.getId(), groupId, page);
        return ResponseEntity.ok(response);
    }
}
