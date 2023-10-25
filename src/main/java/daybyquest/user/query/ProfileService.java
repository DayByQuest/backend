package daybyquest.user.query;

import daybyquest.user.dto.response.ProfileResponse;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class ProfileService {

    private final ProfileDao profileDao;

    public ProfileService(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public List<ProfileResponse> getProfilesByIdIn(final Long userId, final List<Long> ids) {
        final List<Profile> profiles = profileDao.findAllByUserIdIn(userId, ids);
        return profiles.stream().map(ProfileResponse::of).toList();
    }
}
