package daybyquest.quest.presentation;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.SaveQuestDetailService;
import daybyquest.quest.application.SaveQuestService;
import daybyquest.quest.application.SendQuestLabelsService;
import daybyquest.quest.application.SubscribeQuestLabelsService;
import daybyquest.quest.dto.request.QuestLabelsRequest;
import daybyquest.quest.dto.request.SaveQuestDetailRequest;
import daybyquest.quest.dto.request.SaveQuestRequest;
import daybyquest.quest.dto.response.SaveQuestResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class QuestCommandApi {

    private final SaveQuestService saveQuestService;

    private final SaveQuestDetailService saveQuestDetailService;

    private final SubscribeQuestLabelsService subscribeQuestLabelsService;

    private final SendQuestLabelsService sendQuestLabelsService;

    public QuestCommandApi(final SaveQuestService saveQuestService,
            final SaveQuestDetailService saveQuestDetailService,
            final SubscribeQuestLabelsService subscribeQuestLabelsService,
            final SendQuestLabelsService sendQuestLabelsService) {
        this.saveQuestService = saveQuestService;
        this.saveQuestDetailService = saveQuestDetailService;
        this.subscribeQuestLabelsService = subscribeQuestLabelsService;
        this.sendQuestLabelsService = sendQuestLabelsService;
    }

    @PostMapping("/quest")
    @Authorization(admin = true)
    public ResponseEntity<SaveQuestResponse> saveQuest(final AccessUser accessUser,
            @RequestPart SaveQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long questId = saveQuestService.invoke(request, files);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @PostMapping("/quest/{questId}/detail")
    @Authorization(admin = true)
    public ResponseEntity<SaveQuestResponse> saveQuestDetail(final AccessUser accessUser,
            @PathVariable final Long questId, @RequestBody @Valid SaveQuestDetailRequest request) {
        saveQuestDetailService.invoke(questId, request);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @GetMapping(value = "/quest/{questId}/labels", produces = TEXT_EVENT_STREAM_VALUE)
    @Authorization(admin = true)
    public ResponseEntity<SseEmitter> subscribeQuestLabels(final AccessUser accessUser,
            @PathVariable final Long questId) {
        final SseEmitter emitter = subscribeQuestLabelsService.invoke(questId);
        return ResponseEntity.ok(emitter);
    }

    @PatchMapping(value = "/quest/{questId}/shot")
    @Authorization(admin = true)
    public ResponseEntity<Void> sendQuestLabels(final AccessUser accessUser,
            @PathVariable final Long questId, @RequestBody final QuestLabelsRequest request) {
        sendQuestLabelsService.invoke(questId, request);
        return ResponseEntity.ok().build();
    }
}
