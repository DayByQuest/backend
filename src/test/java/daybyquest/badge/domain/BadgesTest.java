package daybyquest.badge.domain;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.NotExistBadgeException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BadgesTest {

    @Mock
    private BadgeRepository badgeRepository;
    @InjectMocks
    private Badges badges;

    @Test
    void 뱃지를_저장한다() {
        // given & when
        badges.save(BADGE_1.생성());

        // then
        then(badgeRepository).should().save(any(Badge.class));
    }

    @Test
    void 뱃지_ID_존재_여부를_검증한다() {
        // given
        final Long badgeId = 1L;
        given(badgeRepository.existsById(badgeId)).willReturn(true);

        // when
        badges.validateExistentById(badgeId);

        // then
        then(badgeRepository).should().existsById(badgeId);
    }

    @Test
    void 뱃지_ID_존재_여부_검증_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> badges.validateExistentById(1L))
                .isInstanceOf(NotExistBadgeException.class);
    }

    @Test
    void ID를_통해_뱃지를_조회한다() {
        // given
        final Long badgeId = 1L;
        given(badgeRepository.findById(badgeId)).willReturn(Optional.of(BADGE_1.생성(badgeId)));

        // when
        final Badge actual = badges.getById(badgeId);

        // then
        assertAll(() -> {
            then(badgeRepository).should().findById(badgeId);
            assertThat(actual.getId()).isEqualTo(badgeId);
            assertThat(actual.getName()).isEqualTo(BADGE_1.name);
            assertThat(actual.getImage()).isEqualTo(BADGE_1.사진());
        });
    }

    @Test
    void ID를_통한_조회_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> badges.getById(1L))
                .isInstanceOf(NotExistBadgeException.class);
    }
}
