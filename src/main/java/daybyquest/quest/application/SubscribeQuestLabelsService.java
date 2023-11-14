package daybyquest.quest.application;

import daybyquest.quest.domain.Quests;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class SubscribeQuestLabelsService {

    private final Quests quests;

    private final QuestSseEmitters questSseEmitters;

    public SubscribeQuestLabelsService(final Quests quests, final QuestSseEmitters questSseEmitters) {
        this.quests = quests;
        this.questSseEmitters = questSseEmitters;
    }

    public SseEmitter invoke(final Long questId) {
        quests.validateExistentById(questId);
        return questSseEmitters.subscribe(questId);
    }
}
