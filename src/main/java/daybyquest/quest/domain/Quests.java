package daybyquest.quest.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_EXIST_REWARD;
import static daybyquest.global.error.ExceptionCode.EXCEED_GROUP_QUEST;
import static daybyquest.quest.domain.QuestState.ACTIVE;
import static daybyquest.quest.domain.QuestState.NEED_LABEL;

import daybyquest.badge.domain.Badges;
import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistQuestException;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Quests {

    private static final int MAX_GROUP_QUEST = 10;

    private static final List<QuestState> QUEST_STATE_FOR_COUNT = List.of(NEED_LABEL, ACTIVE);

    private final QuestRepository questRepository;

    private final Badges badges;

    Quests(final QuestRepository questRepository, final Badges badges) {
        this.questRepository = questRepository;
        this.badges = badges;
    }

    public Quest save(final Quest quest) {
        if (quest.getBadgeId() != null) {
            badges.validateExistentById(quest.getBadgeId());
            validateNotExistentByBadgeId(quest.getBadgeId());
        }
        validateGroupQuest(quest);
        return questRepository.save(quest);
    }

    private void validateGroupQuest(final Quest quest) {
        if (!quest.isGroupQuest()) {
            return;
        }
        if (questRepository.countByGroupIdAndStateIn(quest.getGroupId(), QUEST_STATE_FOR_COUNT) >=
                MAX_GROUP_QUEST) {
            throw new InvalidDomainException(EXCEED_GROUP_QUEST);
        }
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
