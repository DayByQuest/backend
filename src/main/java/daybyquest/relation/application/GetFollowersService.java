package daybyquest.relation.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.relation.query.FollowDao;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.ProfileService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetFollowersService {

    private final FollowDao followDao;

    private final ProfileService profileService;

    public GetFollowersService(final FollowDao followDao, final ProfileService profileService) {
        this.followDao = followDao;
        this.profileService = profileService;
    }

    @Transactional(readOnly = true)
    public PageProfilesResponse invoke(final Long loginId, final NoOffsetIdPage page) {
        final LongIdList targetIds = followDao.getFollowerIds(loginId, page);
        final List<ProfileResponse> profiles = profileService.getProfilesByIdIn(loginId, targetIds.getIds());
        return new PageProfilesResponse(profiles, targetIds.getLastId());
    }
}
