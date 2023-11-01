package daybyquest.participant.presentation;

import daybyquest.auth.domain.AccessUser;
import daybyquest.participant.application.ContinueQuestService;
import daybyquest.participant.application.DeleteParticipantService;
import daybyquest.participant.application.FinishQuestService;
import daybyquest.participant.application.SaveParticipantService;
import daybyquest.participant.application.TakeRewardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantCommandApi {

    private final SaveParticipantService saveParticipantService;

    private final DeleteParticipantService deleteParticipantService;

    private final TakeRewardService takeRewardService;

    private final FinishQuestService finishQuestService;

    private final ContinueQuestService continueQuestService;

    public ParticipantCommandApi(final SaveParticipantService saveParticipantService,
            final DeleteParticipantService deleteParticipantService,
            final TakeRewardService takeRewardService, final FinishQuestService finishQuestService,
            final ContinueQuestService continueQuestService) {
        this.saveParticipantService = saveParticipantService;
        this.deleteParticipantService = deleteParticipantService;
        this.takeRewardService = takeRewardService;
        this.finishQuestService = finishQuestService;
        this.continueQuestService = continueQuestService;
    }

    @PostMapping("/quest/{questId}/accept")
    public ResponseEntity<Void> saveParticipant(final AccessUser accessUser,
            @PathVariable final Long questId) {
        saveParticipantService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/quest/{questId}/accept")
    public ResponseEntity<Void> deleteParticipant(final AccessUser accessUser,
            @PathVariable final Long questId) {
        deleteParticipantService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quest/{questId}/reward")
    public ResponseEntity<Void> takeReward(final AccessUser accessUser,
            @PathVariable final Long questId) {
        takeRewardService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quest/{questId}/finish")
    public ResponseEntity<Void> finishQuest(final AccessUser accessUser,
            @PathVariable final Long questId) {
        finishQuestService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/quest/{questId}/continue")
    public ResponseEntity<Void> continueQuest(final AccessUser accessUser,
            @PathVariable final Long questId) {
        continueQuestService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok().build();
    }
}
