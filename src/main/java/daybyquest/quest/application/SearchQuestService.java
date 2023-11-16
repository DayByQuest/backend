package daybyquest.quest.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.quest.dto.response.PageQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.quest.query.QuestDao;
import daybyquest.quest.query.QuestData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchQuestService {

    private final QuestDao questDao;

    public SearchQuestService(final QuestDao questDao) {
        this.questDao = questDao;
    }

    @Transactional(readOnly = true)
    public PageQuestsResponse invoke(final Long loginId, final String keyword, final NoOffsetIdPage page) {
        final LongIdList ids = questDao.findIdsByTitleLike(keyword, page);
        final List<QuestData> questData = questDao.findAllByIdIn(loginId, ids.getIds());
        final List<QuestResponse> responses = questData.stream().map(QuestResponse::of).toList();
        return new PageQuestsResponse(responses, ids.getLastId());
    }
}
