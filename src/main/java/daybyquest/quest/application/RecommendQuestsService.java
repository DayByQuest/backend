package daybyquest.quest.application;

import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.quest.query.QuestDao;
import daybyquest.quest.query.QuestData;
import daybyquest.quest.query.QuestRecommendDao;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendQuestsService {

    private static final int MAX_RECOMMENDATION_COUNT = 5;

    private final Users users;

    private final QuestRecommendDao recommendDao;

    private final QuestDao questDao;

    public RecommendQuestsService(final Users users, final QuestRecommendDao recommendDao,
            final QuestDao questDao) {
        this.users = users;
        this.recommendDao = recommendDao;
        this.questDao = questDao;
    }

    @Transactional(readOnly = true)
    public MultipleQuestsResponse invoke(final Long loginId) {
        final User user = users.getById(loginId);
        final List<Long> ids = recommendDao.findTopNNormalQuestIdsByInterestIn(MAX_RECOMMENDATION_COUNT,
                user.getInterests());
        final List<QuestData> questData = questDao.findAllByIdIn(loginId, ids);
        final List<QuestResponse> responses = questData.stream().map(QuestResponse::of).toList();
        return new MultipleQuestsResponse(responses);
    }
}
