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
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(FollowDaoQuerydslImpl.class)
public class FollowDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private FollowDaoQuerydslImpl followDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 팔로잉_ID_리스트를_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        final User david = entityManager.merge(DAVID.생성());

        entityManager.persist(new Follow(bob.getId(), alice.getId()));
        entityManager.persist(new Follow(bob.getId(), charlie.getId()));
        entityManager.persist(new Follow(bob.getId(), david.getId()));

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
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        final User david = entityManager.merge(DAVID.생성());

        entityManager.persist(new Follow(alice.getId(), bob.getId()));
        entityManager.persist(new Follow(charlie.getId(), bob.getId()));
        entityManager.persist(new Follow(david.getId(), bob.getId()));

        final List<Long> userIds = List.of(alice.getId(), charlie.getId(), david.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = followDao.getFollowerIds(bob.getId(), page);

        // then
        assertThat(ids.getIds()).containsExactlyInAnyOrderElementsOf(userIds);
    }
}
