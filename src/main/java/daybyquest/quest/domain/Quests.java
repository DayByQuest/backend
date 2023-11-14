package daybyquest.quest.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_EXIST_REWARD;

import daybyquest.badge.domain.Badges;
import daybyquest.global.error.exception.InvalidDomainException;
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
            validateNotExistentByBadgeId(quest.getBadgeId());
        }
        return questRepository.save(quest).getId();
    }

    public Quest getById(final Long id) {
        return questRepository.findById(id).orElseThrow(NotExistQuestException::new);
    }

    public void validateExistentById(final Long id) {
        if (!questRepository.existsById(id)) {
            throw new NotExistQuestException();
        }
    }

    private void validateNotExistentByBadgeId(final Long badgeId) {
        if (questRepository.existsByBadgeId(badgeId)) {
            throw new InvalidDomainException(ALREADY_EXIST_REWARD);
        }
    }

    public String getLabelById(final Long id) {
        return getById(id).getLabel();
    }
}
