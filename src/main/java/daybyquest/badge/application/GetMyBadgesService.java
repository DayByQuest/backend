package daybyquest.badge.application;

import daybyquest.badge.dto.response.BadgeResponse;
import daybyquest.badge.dto.response.PageBadgesResponse;
import daybyquest.badge.query.BadgeDao;
import daybyquest.badge.query.BadgeData;
import daybyquest.global.constant.TimeConstant;
import daybyquest.global.query.NoOffsetTimePage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetMyBadgesService {

    private final BadgeDao badgeDao;

    public GetMyBadgesService(final BadgeDao badgeDao) {
        this.badgeDao = badgeDao;
    }

    @Transactional(readOnly = true)
    public PageBadgesResponse invoke(final Long loginId, final NoOffsetTimePage page) {
        final List<BadgeData> badgeData = badgeDao.getBadgePageByUserIds(loginId, page);
        final List<BadgeResponse> responses = badgeData.stream().map(BadgeResponse::of).toList();
        return new PageBadgesResponse(responses, getLastTime(badgeData));
    }

    private LocalDateTime getLastTime(final List<BadgeData> badgeData) {
        if (badgeData.isEmpty()) {
            return TimeConstant.MAX;
        }
        return badgeData.get(badgeData.size() - 1).getAcquiredAt();
    }
}
