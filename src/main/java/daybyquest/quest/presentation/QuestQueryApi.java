package daybyquest.quest.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.GetQuestByIdService;
import daybyquest.quest.application.GetQuestImagesService;
import daybyquest.quest.application.RecommendQuestsService;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestImagesResponse;
import daybyquest.quest.dto.response.QuestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestQueryApi {

    private final GetQuestByIdService getQuestByIdService;

    private final GetQuestImagesService getQuestImagesService;

    private final RecommendQuestsService recommendQuestsService;

    public QuestQueryApi(final GetQuestByIdService getQuestByIdService,
            final GetQuestImagesService getQuestImagesService,
            final RecommendQuestsService recommendQuestsService) {
        this.getQuestByIdService = getQuestByIdService;
        this.getQuestImagesService = getQuestImagesService;
        this.recommendQuestsService = recommendQuestsService;
    }

    @GetMapping("/quest/{questId}")
    @Authorization(required = false)
    public ResponseEntity<QuestResponse> getQuest(final AccessUser accessUser,
            @PathVariable final Long questId) {
        final QuestResponse response = getQuestByIdService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quest/{questId}/image")
    public ResponseEntity<QuestImagesResponse> getQuestImages(@PathVariable final Long questId) {
        final QuestImagesResponse response = getQuestImagesService.invoke(questId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/quest/recommendation")
    @Authorization
    public ResponseEntity<MultipleQuestsResponse> recommendQuests(final AccessUser accessUser) {
        final MultipleQuestsResponse response = recommendQuestsService.invoke(accessUser.getId());
        return ResponseEntity.ok(response);
    }
}
