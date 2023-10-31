package daybyquest.quest.application;

import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.quest.query.QuestDao;
import daybyquest.quest.query.QuestData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetQuestByIdService {

    private final QuestDao questDao;

    public GetQuestByIdService(final QuestDao questDao) {
        this.questDao = questDao;
    }

    @Transactional(readOnly = true)
    public QuestResponse invoke(final Long loginId, final Long id) {
        final QuestData questData = questDao.getById(loginId, id);
        return QuestResponse.of(questData);
    }
}
