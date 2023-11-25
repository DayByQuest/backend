package daybyquest.profile.application;

import daybyquest.badge.dto.response.BadgeResponse;
import daybyquest.badge.dto.response.MultipleBadgesResponse;
import daybyquest.badge.query.BadgeDao;
import daybyquest.profile.domain.ProfileSettings;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPresetBadgeService {

    private final Users users;

    private final ProfileSettings profileSettings;

    private final BadgeDao badgeDao;

    public GetPresetBadgeService(final Users users, final ProfileSettings profileSettings,
            final BadgeDao badgeDao) {
        this.users = users;
        this.profileSettings = profileSettings;
        this.badgeDao = badgeDao;
    }

    @Transactional(readOnly = true)
    public MultipleBadgesResponse invoke(final String username) {
        final Long userId = users.getUserIdByUsername(username);
        final List<Long> badgeIds = profileSettings.getById(userId).getBadgeIds();
        final List<BadgeResponse> responses = badgeDao.findAllByIdIn(badgeIds)
                .stream().map(BadgeResponse::of).toList();
        return new MultipleBadgesResponse(responses);
    }
}
