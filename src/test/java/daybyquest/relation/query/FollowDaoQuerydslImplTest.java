package daybyquest.relation.query;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DAVID;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(FollowDaoQuerydslImpl.class)
public class FollowDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private FollowDaoQuerydslImpl followDao;

    @Test
    void 팔로잉_ID_리스트를_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());

        저장한다(new Follow(bob.getId(), alice.getId()));
        저장한다(new Follow(bob.getId(), charlie.getId()));
        저장한다(new Follow(bob.getId(), david.getId()));

        final List<Long> userIds = List.of(alice.getId(), charlie.getId(), david.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = followDao.getFollowingIds(bob.getId(), page);

        // then
        assertThat(ids.getIds()).containsExactlyInAnyOrderElementsOf(userIds);
    }

    @Test
    void 팔로워_ID_리스트를_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());

        저장한다(new Follow(alice.getId(), bob.getId()));
        저장한다(new Follow(charlie.getId(), bob.getId()));
        저장한다(new Follow(david.getId(), bob.getId()));

        final List<Long> userIds = List.of(alice.getId(), charlie.getId(), david.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = followDao.getFollowerIds(bob.getId(), page);

        // then
        assertThat(ids.getIds()).containsExactlyInAnyOrderElementsOf(userIds);
    }
}
