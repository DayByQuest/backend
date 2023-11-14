package daybyquest.quest.application;

import daybyquest.group.domain.GroupUsers;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.quest.dto.request.SaveGroupQuestDetailRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveGroupQuestDetailService {

    private final Quests quests;

    private final GroupUsers groupUsers;

    public SaveGroupQuestDetailService(final Quests quests, final GroupUsers groupUsers) {
        this.quests = quests;
        this.groupUsers = groupUsers;
    }

    @Transactional
    public void invoke(final Long loginId, final Long questId, final SaveGroupQuestDetailRequest request) {
        final Quest quest = quests.getById(questId);
        groupUsers.validateGroupManager(loginId, quest.getGroupId());
        quest.setDetail(request.getTitle(), request.getContent(), request.getInterest(),
                request.getExpiredAt(), request.getLabel(), null);
    }
}
