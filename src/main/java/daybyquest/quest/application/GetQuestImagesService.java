package daybyquest.quest.application;

import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.response.QuestImagesResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetQuestImagesService {

    private final Quests quests;

    public GetQuestImagesService(final Quests quests) {
        this.quests = quests;
    }

    @Transactional(readOnly = true)
    public QuestImagesResponse invoke(final Long id) {
        final Quest quest = quests.getById(id);
        return QuestImagesResponse.of(quest);
    }
}
