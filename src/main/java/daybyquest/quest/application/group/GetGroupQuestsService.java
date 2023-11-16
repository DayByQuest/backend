package daybyquest.quest.application.group;

import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.quest.query.QuestDao;
import daybyquest.quest.query.QuestData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupQuestsService {

    private final QuestDao questDao;

    public GetGroupQuestsService(final QuestDao questDao) {
        this.questDao = questDao;
    }

    @Transactional(readOnly = true)
    public MultipleQuestsResponse invoke(final Long loginId, final Long groupId) {
        final List<QuestData> questData = questDao.findAllByGroupId(loginId, groupId);
        final List<QuestResponse> responses = questData.stream().map(QuestResponse::of).toList();
        return new MultipleQuestsResponse(responses);
    }
}
