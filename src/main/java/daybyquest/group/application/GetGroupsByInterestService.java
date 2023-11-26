package daybyquest.group.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.PageGroupsResponse;
import daybyquest.group.query.GroupDao;
import daybyquest.group.query.GroupData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupsByInterestService {

    private final GroupDao groupDao;

    public GetGroupsByInterestService(final GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Transactional(readOnly = true)
    public PageGroupsResponse invoke(final Long loginId, final String interest, final NoOffsetIdPage page) {
        final LongIdList ids = groupDao.findIdsByInterest(interest, page);
        final List<GroupData> groupData = groupDao.findAllByIdsIn(loginId, ids.getIds());
        final List<GroupResponse> responses = groupData.stream().map(GroupResponse::of).toList();
        return new PageGroupsResponse(responses, ids.getLastId());
    }
}
