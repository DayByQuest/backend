package daybyquest.profile.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ProfileSettingRepository extends Repository<ProfileSetting, Long> {

    ProfileSetting save(ProfileSetting profileSetting);

    Optional<ProfileSetting> findByUserId(Long userId);

}
