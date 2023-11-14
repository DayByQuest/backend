package daybyquest.quest.application;

import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.request.QuestLabelsRequest;
import daybyquest.quest.dto.response.QuestLabelsResponse;
import org.springframework.stereotype.Service;

@Service
public class SendQuestLabelsService {

    private final Quests quests;

    private final QuestSseEmitters questSseEmitters;

    public SendQuestLabelsService(final Quests quests, final QuestSseEmitters questSseEmitters) {
        this.quests = quests;
        this.questSseEmitters = questSseEmitters;
    }

    public void invoke(final Long questId, final QuestLabelsRequest request) {
        quests.validateExistentById(questId);
        questSseEmitters.send(questId, requestToResponse(request));
    }

    private QuestLabelsResponse requestToResponse(final QuestLabelsRequest request) {
        return new QuestLabelsResponse(request.getLabels());
    }

}
