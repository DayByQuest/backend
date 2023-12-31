package daybyquest.badge.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.badge.dto.response.BadgeResponse;
import daybyquest.badge.dto.response.PageBadgesResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetMyBadgesServiceTest extends ServiceTest {

    @Autowired
    private GetMyBadgesService getMyBadgesService;

    @Test
    void 내_뱃지_목록을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long badge1Id = badges.save(BADGE_1.생성()).getId();
        final Long badge2Id = badges.save(BADGE_2.생성()).getId();
        badges.save(BADGE_3.생성());
        ownings.saveByUserIdAndBadgeId(aliceId, badge1Id);
        ownings.saveByUserIdAndBadgeId(aliceId, badge2Id);

        final List<Long> expected = List.of(badge1Id, badge2Id);

        // when
        final PageBadgesResponse response = getMyBadgesService.invoke(aliceId, 시간_페이지());
        final List<Long> actual = response.badges().stream().map(BadgeResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
