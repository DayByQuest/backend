package daybyquest.quest.domain;

import org.springframework.stereotype.Component;

@Component
public class Quests {

    private final QuestRepository questRepository;

    Quests(final QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    public void save(final Quest quest) {
        questRepository.save(quest);
    }
}
