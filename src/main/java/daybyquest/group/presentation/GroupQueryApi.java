package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.application.GetGroupProfileService;
import daybyquest.group.application.GetGroupUsersService;
import daybyquest.group.application.GetGroupsService;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.user.dto.response.PageProfilesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupQueryApi {

    private final GetGroupProfileService getGroupProfileService;

    private final GetGroupUsersService getGroupUsersService;

    private final GetGroupsService getGroupsService;

    public GroupQueryApi(final GetGroupProfileService getGroupProfileService,
            final GetGroupUsersService getGroupUsersService, final GetGroupsService getGroupsService) {
        this.getGroupProfileService = getGroupProfileService;
        this.getGroupUsersService = getGroupUsersService;
        this.getGroupsService = getGroupsService;
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

    @GetMapping("/group")
    @Authorization
    public ResponseEntity<MultipleGroupsResponse> getGroups(final AccessUser accessUser) {
        final MultipleGroupsResponse response = getGroupsService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }
}
