package daybyquest.badge.domain;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.user.domain.Users;
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
}
