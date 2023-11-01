package daybyquest.quest.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.GetQuestByIdService;
import daybyquest.quest.application.SaveQuestService;
import daybyquest.quest.dto.request.SaveQuestRequest;
import daybyquest.quest.dto.response.QuestResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QuestCommandApi {

    private final SaveQuestService saveQuestService;

    private final GetQuestByIdService getQuestByIdService;

    public QuestCommandApi(final SaveQuestService saveQuestService,
            final GetQuestByIdService getQuestByIdService) {
        this.saveQuestService = saveQuestService;
        this.getQuestByIdService = getQuestByIdService;
    }

    @PostMapping("/quest")
    @Authorization(admin = true)
    public ResponseEntity<QuestResponse> saveQuest(final AccessUser accessUser,
            @RequestPart SaveQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long questId = saveQuestService.invoke(request, files);
        final QuestResponse response = getQuestByIdService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok(response);
    }
}
