package daybyquest.badge.domain;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BadgeTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육"})
    void 뱃지_이름이_1_에서_15_글자가_아니면_예외를_던진다(final String name) {
        // given & when & then
        assertThatThrownBy(() -> new Badge(name, BADGE_1.사진()))
                .isInstanceOf(InvalidDomainException.class);
    }
}
