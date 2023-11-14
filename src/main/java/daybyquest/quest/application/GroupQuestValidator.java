package daybyquest.quest.application;

import daybyquest.group.domain.GroupUsers;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GroupQuestValidator {

    private final Quests quests;

    private final GroupUsers groupUsers;

    public GroupQuestValidator(final Quests quests, final GroupUsers groupUsers) {
        this.quests = quests;
        this.groupUsers = groupUsers;
    }

    @Transactional(readOnly = true)
    public void validate(final Long loginId, final Long questId) {
        final Quest quest = quests.getById(questId);
        groupUsers.validateGroupManager(loginId, quest.getGroupId());
    }
}
