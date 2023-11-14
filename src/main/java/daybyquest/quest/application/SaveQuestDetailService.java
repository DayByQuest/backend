package daybyquest.quest.application;

import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.request.SaveQuestDetailRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveQuestDetailService {

    private final Quests quests;

    public SaveQuestDetailService(final Quests quests) {
        this.quests = quests;
    }

    @Transactional
    public void invoke(final Long questId, final SaveQuestDetailRequest request) {
        final Quest quest = quests.getById(questId);
        quest.setDetail(request.getTitle(), request.getContent(), request.getInterest(),
                null, request.getLabel(), request.getRewardCount());
    }
}
