package daybyquest.participant.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.participant.application.GetQuestsService;
import daybyquest.participant.domain.ParticipantState;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ParticipantQueryApi {

    private final GetQuestsService getQuestsService;

    public ParticipantQueryApi(final GetQuestsService getQuestsService) {
        this.getQuestsService = getQuestsService;
    }

    @GetMapping("/quest")
    @Authorization
    public ResponseEntity<MultipleQuestsResponse> getQuestsByState(final AccessUser accessUser,
            final ParticipantState state) {
        final MultipleQuestsResponse response = getQuestsService.invoke(accessUser.getId(), state);
        return ResponseEntity.ok(response);
    }
}
