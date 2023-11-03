package daybyquest.badge.query;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.domain.Owning;
import daybyquest.global.query.NoOffsetTimePage;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(BadgeDaoQuerydslImpl.class)
public class BadgeDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private BadgeDaoQuerydslImpl badgeDao;

    @Test
    void 소유한_뱃지_목록을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final Badge badge1 = 저장한다(BADGE_1.생성());
        final Badge badge2 = 저장한다(BADGE_2.생성());
        final Badge badge3 = 저장한다(BADGE_3.생성());

        저장한다(new Owning(bob.getId(), badge1));
        저장한다(new Owning(bob.getId(), badge2));
        저장한다(new Owning(bob.getId(), badge3));

        final NoOffsetTimePage page = new NoOffsetTimePage(null, 5);

        // when
        final List<BadgeData> badgeData = badgeDao.getBadgePageByUserIds(bob.getId(), page);

        // then
        assertThat(badgeData).hasSize(3);
    }

    @Test
    void 소유하지_않은_뱃지는_목록에_포함되지_않는다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final Badge badge1 = 저장한다(BADGE_1.생성());
        저장한다(BADGE_2.생성());
        저장한다(BADGE_3.생성());

        저장한다(new Owning(bob.getId(), badge1));

        final NoOffsetTimePage page = new NoOffsetTimePage(null, 5);

        // when
        final List<BadgeData> badgeData = badgeDao.getBadgePageByUserIds(bob.getId(), page);

        // then
        assertThat(badgeData).hasSize(1);
    }

    @Test
    void 뱃지_데이터는_뱃지_정보와_일치_해야한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final Badge badge1 = 저장한다(BADGE_1.생성());

        저장한다(new Owning(bob.getId(), badge1));

        final NoOffsetTimePage page = new NoOffsetTimePage(null, 5);

        // when
        final List<BadgeData> badgeData = badgeDao.getBadgePageByUserIds(bob.getId(), page);

        // then
        assertAll(() -> {
            assertThat(badgeData).hasSize(1);
            final BadgeData actual = badgeData.get(0);
            assertThat(actual.getName()).isEqualTo(BADGE_1.name);
            assertThat(actual.getImageIdentifier()).isEqualTo(BADGE_1.imageIdentifier);
        });
    }
}
