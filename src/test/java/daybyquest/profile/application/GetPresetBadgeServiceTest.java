package daybyquest.profile.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.badge.dto.response.BadgeResponse;
import daybyquest.badge.dto.response.MultipleBadgesResponse;
import daybyquest.profile.domain.ProfileSetting;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPresetBadgeServiceTest extends ServiceTest {

    @Autowired
    private GetPresetBadgeService getPresetBadgeService;

    @Test
    void 설정된_뱃지_목록을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();

        final Long badge1Id = badges.save(BADGE_1.생성()).getId();
        final Long badge2Id = badges.save(BADGE_2.생성()).getId();
        badges.save(BADGE_3.생성());

        ownings.saveByUserIdAndBadgeId(aliceId, badge1Id);
        ownings.saveByUserIdAndBadgeId(aliceId, badge2Id);

        final List<Long> expected = List.of(badge1Id, badge2Id);
        final ProfileSetting profileSetting = new ProfileSetting(aliceId);
        profileSetting.updateBadgeList(expected);
        profileSettings.save(profileSetting);

        // when
        final MultipleBadgesResponse response = getPresetBadgeService.invoke(ALICE.username);
        final List<Long> actual = response.badges().stream().map(BadgeResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
