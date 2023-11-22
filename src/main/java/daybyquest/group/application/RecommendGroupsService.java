package daybyquest.group.application;

import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.group.query.GroupDao;
import daybyquest.group.query.GroupData;
import daybyquest.group.query.GroupRecommendDao;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecommendGroupsService {

    private static final int MAX_RECOMMENDATION_COUNT = 5;

    private final Users users;

    private final GroupRecommendDao recommendDao;

    private final GroupDao groupDao;

    public RecommendGroupsService(final Users users, final GroupRecommendDao recommendDao,
            final GroupDao groupDao) {
        this.users = users;
        this.recommendDao = recommendDao;
        this.groupDao = groupDao;
    }

    @Transactional(readOnly = true)
    public MultipleGroupsResponse invoke(final Long loginId) {
        final User user = users.getById(loginId);
        final List<Long> ids = getRecommendIds(user.getInterests());
        final List<GroupData> groupData = groupDao.findAllByIdsIn(loginId, ids);
        final List<GroupResponse> responses = groupData.stream().map(GroupResponse::of).toList();
        return new MultipleGroupsResponse(responses);
    }

    private List<Long> getRecommendIds(final Collection<String> interests) {
        if (interests.isEmpty()) {
            return recommendDao.getRecommendIds(MAX_RECOMMENDATION_COUNT);
        }
        return recommendDao.getRecommendIds(MAX_RECOMMENDATION_COUNT, interests);
    }
}
