package daybyquest.group.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.dto.response.GroupUserResponse;
import daybyquest.group.dto.response.PageGroupUsersResponse;
import daybyquest.group.query.GroupUserDao;
import daybyquest.group.query.GroupUserData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupUsersService {

    private final GroupUserDao groupUserDao;

    public GetGroupUsersService(final GroupUserDao groupUserDao) {
        this.groupUserDao = groupUserDao;
    }

    @Transactional(readOnly = true)
    public PageGroupUsersResponse invoke(final Long loginId, final Long groupId, final NoOffsetIdPage page) {
        final LongIdList targetIds = groupUserDao.findUserIdsByGroupId(groupId, page);
        final List<GroupUserData> groupUsers = groupUserDao.findAllByUserIdsIn(loginId, targetIds.getIds());
        final List<GroupUserResponse> responses = groupUsers.stream().map(GroupUserResponse::of).toList();
        return new PageGroupUsersResponse(responses, targetIds.getLastId());
    }
}
