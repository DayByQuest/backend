package daybyquest.group.application;

import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.group.query.GroupDao;
import daybyquest.group.query.GroupData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupsService {

    private final GroupDao groupDao;

    public GetGroupsService(final GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Transactional(readOnly = true)
    public MultipleGroupsResponse invoke(final Long loginId) {
        final List<GroupData> groupData = groupDao.findAllByUserId(loginId);
        final List<GroupResponse> responses = groupData.stream().map(GroupResponse::of).toList();
        return new MultipleGroupsResponse(responses);
    }
}
