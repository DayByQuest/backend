package daybyquest.quest.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.SaveGroupQuestDetailService;
import daybyquest.quest.application.SaveGroupQuestService;
import daybyquest.quest.application.SaveQuestDetailService;
import daybyquest.quest.application.SaveQuestService;
import daybyquest.quest.dto.request.SaveGroupQuestDetailRequest;
import daybyquest.quest.dto.request.SaveGroupQuestRequest;
import daybyquest.quest.dto.request.SaveQuestDetailRequest;
import daybyquest.quest.dto.request.SaveQuestRequest;
import daybyquest.quest.dto.response.SaveQuestResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class QuestCommandApi {

    private final SaveQuestService saveQuestService;

    private final SaveGroupQuestService saveGroupQuestService;

    private final SaveQuestDetailService saveQuestDetailService;

    private final SaveGroupQuestDetailService saveGroupQuestDetailService;

    public QuestCommandApi(final SaveQuestService saveQuestService,
            final SaveGroupQuestService saveGroupQuestService,
            final SaveQuestDetailService saveQuestDetailService,
            final SaveGroupQuestDetailService saveGroupQuestDetailService) {
        this.saveQuestService = saveQuestService;
        this.saveGroupQuestService = saveGroupQuestService;
        this.saveQuestDetailService = saveQuestDetailService;
        this.saveGroupQuestDetailService = saveGroupQuestDetailService;
    }

    @PostMapping("/quest")
    @Authorization(admin = true)
    public ResponseEntity<SaveQuestResponse> saveQuest(final AccessUser accessUser,
            @RequestPart SaveQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long questId = saveQuestService.invoke(request, files);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @PostMapping("/group/quest")
    @Authorization
    public ResponseEntity<SaveQuestResponse> saveGroupQuest(final AccessUser accessUser,
            @RequestPart SaveGroupQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long questId = saveGroupQuestService.invoke(accessUser.getId(), request, files);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @PostMapping("/quest/{questId}/detail")
    @Authorization(admin = true)
    public ResponseEntity<SaveQuestResponse> saveQuestDetail(final AccessUser accessUser,
            @PathVariable final Long questId, @RequestBody @Valid SaveQuestDetailRequest request) {
        saveQuestDetailService.invoke(questId, request);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @PostMapping("/group/{questId}/quest/detail")
    @Authorization
    public ResponseEntity<SaveQuestResponse> saveGroupQuestDetail(final AccessUser accessUser,
            @PathVariable final Long questId, @RequestBody @Valid SaveGroupQuestDetailRequest request) {
        saveGroupQuestDetailService.invoke(accessUser.getId(), questId, request);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }
}
