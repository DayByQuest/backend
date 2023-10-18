package daybyquest.user.application;

import daybyquest.user.domain.Profile;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.ProfileDao;
import org.springframework.stereotype.Service;

@Service
public class GetProfileByUsernameService {

    private final ProfileDao profileDao;

    public GetProfileByUsernameService(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public ProfileResponse invoke(final Long loginId, final String username) {
        final Profile profile = profileDao.getByUsername(loginId, username);
        return ProfileResponse.of(profile);
    }
}
