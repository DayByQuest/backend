package daybyquest.quest.domain;

import daybyquest.badge.domain.Badges;
import daybyquest.global.error.exception.NotExistQuestException;
import org.springframework.stereotype.Component;

@Component
public class Quests {

    private final QuestRepository questRepository;

    private final Badges badges;

    Quests(final QuestRepository questRepository, final Badges badges) {
        this.questRepository = questRepository;
        this.badges = badges;
    }

    public Long save(final Quest quest) {
        if (quest.getBadgeId() != null) {
            badges.validateExistentById(quest.getBadgeId());
        }
        return questRepository.save(quest).getId();
    }

    public Quest getById(final Long id) {
        return questRepository.findById(id).orElseThrow(NotExistQuestException::new);
    }
}
