package daybyquest.user.application;

import daybyquest.user.domain.Profile;
import daybyquest.user.domain.UserImages;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.ProfileDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetProfileByUsernameService {

    private final ProfileDao profileDao;

    private final UserImages userImages;

    public GetProfileByUsernameService(final ProfileDao profileDao, final UserImages userImages) {
        this.profileDao = profileDao;
        this.userImages = userImages;
    }

    @Transactional(readOnly = true)
    public ProfileResponse invoke(final Long loginId, final String username) {
        final Profile profile = profileDao.getByUsername(loginId, username);
        return ProfileResponse.of(profile, userImages.getPublicUrl(profile.getImageIdentifier()));
    }
}
