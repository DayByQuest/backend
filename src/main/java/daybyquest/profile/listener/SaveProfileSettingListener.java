package daybyquest.profile.listener;

import daybyquest.profile.domain.ProfileSetting;
import daybyquest.profile.domain.ProfileSettings;
import daybyquest.user.domain.UserSavedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SaveProfileSettingListener {

    private final ProfileSettings profileSettings;

    public SaveProfileSettingListener(final ProfileSettings profileSettings) {
        this.profileSettings = profileSettings;
    }

    @EventListener
    public void listenUserSavedEvent(final UserSavedEvent event) {
        final ProfileSetting profileSetting = new ProfileSetting(event.userId());
        profileSettings.save(profileSetting);
    }
}
