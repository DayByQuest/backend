package daybyquest.profile.domain;

import daybyquest.global.error.exception.NotExistUserException;
import org.springframework.stereotype.Component;

@Component
public class ProfileSettings {

    private final ProfileSettingRepository profileSettingRepository;

    ProfileSettings(final ProfileSettingRepository profileSettingRepository) {
        this.profileSettingRepository = profileSettingRepository;
    }

    public void save(final ProfileSetting profileSetting) {
        profileSettingRepository.save(profileSetting);
    }

    public ProfileSetting getById(final Long userId) {
        return profileSettingRepository.findByUserId(userId).orElseThrow(NotExistUserException::new);
    }
}
