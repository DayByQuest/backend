package daybyquest.quest.presentation;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.quest.application.group.SaveGroupQuestDetailService;
import daybyquest.quest.application.group.SaveGroupQuestService;
import daybyquest.quest.application.group.SubscribeGroupQuestLabelsService;
import daybyquest.quest.dto.request.SaveGroupQuestDetailRequest;
import daybyquest.quest.dto.request.SaveGroupQuestRequest;
import daybyquest.quest.dto.response.SaveQuestResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class GroupQuestCommandApi {

    private final SaveGroupQuestService saveGroupQuestService;

    private final SaveGroupQuestDetailService saveGroupQuestDetailService;

    private final SubscribeGroupQuestLabelsService subscribeGroupQuestLabelsService;

    public GroupQuestCommandApi(final SaveGroupQuestService saveGroupQuestService,
            final SaveGroupQuestDetailService saveGroupQuestDetailService,
            final SubscribeGroupQuestLabelsService subscribeGroupQuestLabelsService) {
        this.saveGroupQuestService = saveGroupQuestService;
        this.saveGroupQuestDetailService = saveGroupQuestDetailService;
        this.subscribeGroupQuestLabelsService = subscribeGroupQuestLabelsService;
    }

    @PostMapping("/group/quest")
    @Authorization
    public ResponseEntity<SaveQuestResponse> saveGroupQuest(final AccessUser accessUser,
            @RequestPart SaveGroupQuestRequest request,
            @RequestPart List<MultipartFile> files) {
        final Long questId = saveGroupQuestService.invoke(accessUser.getId(), request, files);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @PostMapping("/group/{questId}/quest/detail")
    @Authorization
    public ResponseEntity<SaveQuestResponse> saveGroupQuestDetail(final AccessUser accessUser,
            @PathVariable final Long questId, @RequestBody @Valid SaveGroupQuestDetailRequest request) {
        saveGroupQuestDetailService.invoke(accessUser.getId(), questId, request);
        return ResponseEntity.ok(new SaveQuestResponse(questId));
    }

    @GetMapping(value = "/group/{questId}/quest/labels", produces = TEXT_EVENT_STREAM_VALUE)
    @Authorization
    public ResponseEntity<SseEmitter> subscribeGroupQuestLabels(final AccessUser accessUser,
            @PathVariable final Long questId) {
        final SseEmitter emitter = subscribeGroupQuestLabelsService.invoke(accessUser.getId(), questId);
        return ResponseEntity.ok(emitter);
    }
}
