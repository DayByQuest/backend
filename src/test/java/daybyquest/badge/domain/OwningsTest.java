package daybyquest.badge.domain;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_2;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_3;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.user.domain.Users;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OwningsTest {

    @Mock
    private OwningRepository owningRepository;

    @Mock
    private Badges badges;

    @Mock
    private Users users;

    @InjectMocks
    private Ownings ownings;

    @Test
    void 사용자_ID와_뱃지_ID를_통해_소유를_저장한다() {
        // given
        final Long userId = 1L;
        final Long badgeId = 2L;
        given(badges.getById(badgeId)).willReturn(BADGE_1.생성(badgeId));

        // when
        ownings.saveByUserIdAndBadgeId(userId, badgeId);

        // then
        then(owningRepository).should().save(any(Owning.class));
    }

    @Test
    void 저장_시_사용자_ID_존재_여부를_검증한다() {
        // given
        final Long userId = 1L;
        final Long badgeId = 2L;
        given(badges.getById(badgeId)).willReturn(BADGE_1.생성(badgeId));

        // when
        ownings.saveByUserIdAndBadgeId(userId, badgeId);

        // then
        then(users).should().validateExistentById(userId);
    }

    @Test
    void 사용자가_뱃지들을_보유하고_있는지_ID를_통해_검증한다() {
        // given
        final Long userId = 1L;
        final List<Owning> owningList = List.of(
                new Owning(userId, BADGE_1.생성(2L)),
                new Owning(userId, BADGE_2.생성(3L)),
                new Owning(userId, BADGE_3.생성(4L))
        );
        final List<Long> badgeIds = owningList.stream().map(Owning::getBadgeId).toList();
        given(owningRepository.findAllByUserIdAndBadgeIdIn(userId, badgeIds)).willReturn(owningList);

        // when
        ownings.validateOwningByBadgeIds(userId, badgeIds);

        // then
        then(owningRepository).should().findAllByUserIdAndBadgeIdIn(userId, badgeIds);
    }

    @Test
    void 사용자가_뱃지들을_보유하고_있는지_검증_시_하나라도_없으면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final List<Owning> owningList = List.of(
                new Owning(userId, BADGE_1.생성(2L)),
                new Owning(userId, BADGE_2.생성(3L))
        );
        final List<Long> badgeIds = List.of(2L, 3L, 4L);
        given(owningRepository.findAllByUserIdAndBadgeIdIn(userId, badgeIds)).willReturn(owningList);

        // when & then
        assertThatThrownBy(() -> ownings.validateOwningByBadgeIds(userId, badgeIds))
                .isInstanceOf(InvalidDomainException.class);
    }
}
