package daybyquest.profile.application;

import daybyquest.badge.domain.Ownings;
import daybyquest.profile.domain.ProfileSetting;
import daybyquest.profile.domain.ProfileSettings;
import daybyquest.profile.dto.request.SaveBadgeListRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveBadgeListService {

    private final ProfileSettings profileSettings;

    private final Ownings ownings;

    public SaveBadgeListService(final ProfileSettings profileSettings, final Ownings ownings) {
        this.profileSettings = profileSettings;
        this.ownings = ownings;
    }

    @Transactional
    public void invoke(final Long loginId, final SaveBadgeListRequest request) {
        if (request.getBadgeIds() != null) {
            ownings.validateOwningByBadgeIds(loginId, request.getBadgeIds());
        }
        final ProfileSetting profileSetting = profileSettings.getById(loginId);
        profileSetting.updateBadgeList(request.getBadgeIds());
    }
}
