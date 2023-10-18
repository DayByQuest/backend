package daybyquest.user.application;

import daybyquest.user.domain.Profile;
import daybyquest.user.dto.response.MyProfileResponse;
import daybyquest.user.query.ProfileDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMyProfileService {

    private final ProfileDao profileDao;

    public GetMyProfileService(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Transactional(readOnly = true)
    public MyProfileResponse invoke(final Long loginId) {
        final Profile profile = profileDao.getMine(loginId);
        return MyProfileResponse.of(profile);
    }
}
