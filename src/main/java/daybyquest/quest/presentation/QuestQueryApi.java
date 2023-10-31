package daybyquest.quest.presentation;

import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.GetQuestByIdService;
import daybyquest.quest.dto.response.QuestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestQueryApi {

    private final GetQuestByIdService getQuestByIdService;

    public QuestQueryApi(final GetQuestByIdService getQuestByIdService) {
        this.getQuestByIdService = getQuestByIdService;
    }

    @GetMapping("/quest/{questId}")
    public ResponseEntity<QuestResponse> getQuest(final AccessUser accessUser,
            @PathVariable final Long questId) {
        final QuestResponse response = getQuestByIdService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok(response);
    }
}
