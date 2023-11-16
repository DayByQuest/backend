package daybyquest.user.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.user.dto.response.PageProfilesResponse;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.ProfileDao;
import daybyquest.user.query.ProfileService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SearchUserService {

    private final ProfileDao profileDao;

    private final ProfileService profileService;

    public SearchUserService(final ProfileDao profileDao, final ProfileService profileService) {
        this.profileDao = profileDao;
        this.profileService = profileService;
    }

    @Transactional(readOnly = true)
    public PageProfilesResponse invoke(final Long loginId, final String keyword, final NoOffsetIdPage page) {
        final LongIdList ids = profileDao.findIdsByUsernameLike(keyword, page);
        final List<ProfileResponse> response = profileService.getProfilesByIdIn(loginId, ids.getIds());
        return new PageProfilesResponse(response, ids.getLastId());
    }
}
