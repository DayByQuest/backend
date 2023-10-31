package daybyquest.participant.presentation;

import daybyquest.auth.domain.AccessUser;
import daybyquest.participant.application.DeleteParticipantService;
import daybyquest.participant.application.SaveParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantCommandApi {

    private final SaveParticipantService saveParticipantService;

    private final DeleteParticipantService deleteParticipantService;

    public ParticipantCommandApi(final SaveParticipantService saveParticipantService,
            final DeleteParticipantService deleteParticipantService) {
        this.saveParticipantService = saveParticipantService;
        this.deleteParticipantService = deleteParticipantService;
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
}
