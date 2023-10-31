package daybyquest.quest.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.SaveQuestService;
import daybyquest.quest.dto.request.SaveQuestRequest;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QuestCommandApi {

    private final SaveQuestService saveQuestService;

    public QuestCommandApi(final SaveQuestService saveQuestService) {
        this.saveQuestService = saveQuestService;
    }

    @PostMapping("/quest")
    @Authorization(admin = true)
    public ResponseEntity<Void> saveQuest(final AccessUser accessUser,
            @RequestPart SaveQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        saveQuestService.invoke(request, files);
        return ResponseEntity.ok().build();
    }
}
