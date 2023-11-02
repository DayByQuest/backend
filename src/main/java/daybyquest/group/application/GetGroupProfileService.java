package daybyquest.group.application;

import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.query.GroupDao;
import daybyquest.group.query.GroupData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupProfileService {

    private final GroupDao groupDao;

    public GetGroupProfileService(final GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    @Transactional(readOnly = true)
    public GroupResponse invoke(final Long loginId, final Long groupId) {
        final GroupData groupData = groupDao.getById(loginId, groupId);
        return GroupResponse.of(groupData);
    }
}
