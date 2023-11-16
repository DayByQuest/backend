package daybyquest.quest.presentation;

import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.group.GetGroupQuestsService;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupQuestQueryApi {

    private final GetGroupQuestsService getGroupQuestsService;

    public GroupQuestQueryApi(final GetGroupQuestsService getGroupQuestsService) {
        this.getGroupQuestsService = getGroupQuestsService;
    }

    @GetMapping("/group/{groupId}/quest")
    public ResponseEntity<MultipleQuestsResponse> getGroupQuests(final AccessUser accessUser,
            @PathVariable final Long groupId) {
        final MultipleQuestsResponse response = getGroupQuestsService.invoke(accessUser.getId(), groupId);
        return ResponseEntity.ok(response);
    }
}
