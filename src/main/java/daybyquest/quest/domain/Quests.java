package daybyquest.quest.domain;

import daybyquest.global.error.exception.NotExistQuestException;
import org.springframework.stereotype.Component;

@Component
public class Quests {

    private final QuestRepository questRepository;

    Quests(final QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public Long save(final Quest quest) {
        return questRepository.save(quest).getId();
    }

    public Quest getById(final Long id) {
        return questRepository.findById(id).orElseThrow(NotExistQuestException::new);
    }
}
