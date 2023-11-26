package daybyquest.group.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.application.CheckGroupNameService;
import daybyquest.group.application.GetGroupProfileService;
import daybyquest.group.application.GetGroupUsersService;
import daybyquest.group.application.GetGroupsByInterestService;
import daybyquest.group.application.GetGroupsService;
import daybyquest.group.application.RecommendGroupsService;
import daybyquest.group.application.SearchGroupService;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.group.dto.response.PageGroupUsersResponse;
import daybyquest.group.dto.response.PageGroupsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupQueryApi {

    private final CheckGroupNameService checkGroupNameService;

    private final GetGroupProfileService getGroupProfileService;

    private final GetGroupUsersService getGroupUsersService;

    private final GetGroupsService getGroupsService;

    private final RecommendGroupsService recommendGroupsService;

    private final SearchGroupService searchGroupService;

    private final GetGroupsByInterestService getGroupsByInterestService;

    public GroupQueryApi(final CheckGroupNameService checkGroupNameService,
            final GetGroupProfileService getGroupProfileService,
            final GetGroupUsersService getGroupUsersService, final GetGroupsService getGroupsService,
            final RecommendGroupsService recommendGroupsService,
            final SearchGroupService searchGroupService,
            final GetGroupsByInterestService getGroupsByInterestService) {
        this.checkGroupNameService = checkGroupNameService;
        this.getGroupProfileService = getGroupProfileService;
        this.getGroupUsersService = getGroupUsersService;
        this.getGroupsService = getGroupsService;
        this.recommendGroupsService = recommendGroupsService;
        this.searchGroupService = searchGroupService;
        this.getGroupsByInterestService = getGroupsByInterestService;
    }

    @GetMapping("/group/{groupName}/check")
    public ResponseEntity<Void> checkGroupName(@PathVariable final String groupName) {
        checkGroupNameService.invoke(groupName);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<PageGroupUsersResponse> getGroupUsers(final AccessUser accessUser,
            @PathVariable final Long groupId, final NoOffsetIdPage page) {
        final PageGroupUsersResponse response = getGroupUsersService.invoke(accessUser.getId(), groupId,
                page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group")
    @Authorization
    public ResponseEntity<MultipleGroupsResponse> getGroups(final AccessUser accessUser) {
        final MultipleGroupsResponse response = getGroupsService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/group/recommendation")
    @Authorization
    public ResponseEntity<MultipleGroupsResponse> recommendGroups(final AccessUser accessUser) {
        final MultipleGroupsResponse response = recommendGroupsService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/group")
    @Authorization
    public ResponseEntity<PageGroupsResponse> searchGroup(final AccessUser accessUser,
            @RequestParam final String keyword, final NoOffsetIdPage page) {
        final PageGroupsResponse response = searchGroupService.invoke(accessUser.getId(), keyword, page);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/groups")
    @Authorization
    public ResponseEntity<PageGroupsResponse> getGroupsByInterest(final AccessUser accessUser,
            @RequestParam final String interest, final NoOffsetIdPage page) {
        final PageGroupsResponse response = getGroupsByInterestService.invoke(accessUser.getId(), interest,
                page);
        return ResponseEntity.ok(response);
    }
}
