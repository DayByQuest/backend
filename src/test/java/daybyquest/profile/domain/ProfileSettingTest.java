package daybyquest.profile.domain;

import static daybyquest.global.error.ExceptionCode.EXCEED_BADGE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProfileSettingTest {

    @Test
    void 뱃지_ID가_10개를_넘으면_예외를_던진다() {
        // given
        final ProfileSetting profileSetting = new ProfileSetting(1L);
        final List<Long> badgeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 6L, 7L, 8L, 9L, 10L, 11L);

        // when & then
        assertThatThrownBy(() -> profileSetting.updateBadgeList(badgeIds))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(EXCEED_BADGE.getMessage());
    }
}
