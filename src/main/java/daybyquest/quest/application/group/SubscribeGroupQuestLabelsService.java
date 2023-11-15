package daybyquest.quest.application.group;

import daybyquest.quest.application.QuestSseEmitters;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SubscribeGroupQuestLabelsService {

    private final GroupQuestValidator groupQuestValidator;

    private final QuestSseEmitters questSseEmitters;

    public SubscribeGroupQuestLabelsService(final GroupQuestValidator groupQuestValidator,
            final QuestSseEmitters questSseEmitters) {
        this.groupQuestValidator = groupQuestValidator;
        this.questSseEmitters = questSseEmitters;
    }

    public SseEmitter invoke(final Long loginId, final Long questId) {
        groupQuestValidator.validate(loginId, questId);
        return questSseEmitters.subscribe(questId);
    }
}
