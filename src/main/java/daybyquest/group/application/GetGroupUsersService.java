package daybyquest.group.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.query.GroupDao;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.ProfileService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetGroupUsersService {

    private final GroupDao groupDao;

    private final ProfileService profileService;

    public GetGroupUsersService(final GroupDao groupDao, final ProfileService profileService) {
        this.groupDao = groupDao;
        this.profileService = profileService;
    }

    @Transactional(readOnly = true)
    public PageProfilesResponse invoke(final Long loginId, final Long groupId, final NoOffsetIdPage page) {
        final LongIdList targetIds = groupDao.findUserIdsByGroupId(groupId, page);
        final List<ProfileResponse> profiles = profileService.getProfilesByIdIn(loginId, targetIds.getIds());
        return new PageProfilesResponse(profiles, targetIds.getLastId());
    }
}
