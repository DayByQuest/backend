package daybyquest.support.fixture;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.dto.response.BadgeResponse;
import daybyquest.global.constant.TimeConstant;
import daybyquest.image.domain.Image;
import org.springframework.test.util.ReflectionTestUtils;

public enum BadgeFixtures {

    BADGE_1("뱃지1", "badge1.png"),
    BADGE_2("뱃지2", "badge2.png"),
    BADGE_3("뱃지3", "badge3.png"),
    BADGE_4("뱃지4", "badge4.png");

    public final String name;

    public final String imageIdentifier;

    BadgeFixtures(final String name, final String imageIdentifier) {
        this.name = name;
        this.imageIdentifier = imageIdentifier;
    }

    public Badge 생성(final Long id) {
        final Badge badge = new Badge(name, 사진());
        ReflectionTestUtils.setField(badge, "id", id);
        return badge;
    }

    public Badge 생성() {
        return 생성(null);
    }

    public Image 사진() {
        return new Image(imageIdentifier);
    }

    public BadgeResponse 응답(final Long id) {
        return new BadgeResponse(name, imageIdentifier, id, TimeConstant.EXAMPLE);
    }
}
