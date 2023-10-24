package daybyquest.user.query;

import daybyquest.user.domain.UserImages;
import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileDao profileDao;

    private final UserImages userImages;

    public ProfileService(final ProfileDao profileDao, final UserImages userImages) {
        this.profileDao = profileDao;
        this.userImages = userImages;
    }

    public List<ProfileResponse> getProfilesByIdIn(final Long userId, final List<Long> ids) {
        final List<Profile> profiles = profileDao.findAllByUserIdIn(userId, ids);
        return profiles.stream().map((profile) ->
                ProfileResponse.of(profile, userImages.getPublicUrl(profile.getImageIdentifier()))
        ).toList();
    }
}
