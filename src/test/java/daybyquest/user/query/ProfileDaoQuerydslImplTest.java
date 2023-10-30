package daybyquest.user.query;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.NotExistUserException;
import daybyquest.relation.domain.Block;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(ProfileDaoQuerydslImpl.class)
public class ProfileDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private ProfileDaoQuerydslImpl profileDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 사용자_이름으로_프로필을_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());

        // when
        final Profile profile = profileDao.getByUsername(user.getId(), BOB.username);

        // then
        assertAll(() -> {
            assertThat(profile.getUsername()).isEqualTo(BOB.username);
            assertThat(profile.getName()).isEqualTo(BOB.name);
            assertThat(profile.getImageIdentifier()).isEqualTo(BOB.imageIdentifier);
        });
    }

    @Test
    void 사용자_이름이_없으면_예외를_던진다() {
        assertThatThrownBy(() -> profileDao.getByUsername(1L, BOB.username))
                .isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 사용자_ID로_프로필을_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());

        // when
        final Profile profile = profileDao.getById(user.getId(), user.getId());

        // then
        assertAll(() -> {
            assertThat(profile.getUsername()).isEqualTo(BOB.username);
            assertThat(profile.getName()).isEqualTo(BOB.name);
            assertThat(profile.getImageIdentifier()).isEqualTo(BOB.imageIdentifier);
        });
    }

    @Test
    void 사용자_ID가_없으면_예외를_던진다() {
        assertThatThrownBy(() -> profileDao.getById(1L, 1L))
                .isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 내_프로필을_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());

        // when
        final Profile profile = profileDao.getMine(user.getId());

        // then
        assertAll(() -> {
            assertThat(profile.getUsername()).isEqualTo(BOB.username);
            assertThat(profile.getName()).isEqualTo(BOB.name);
            assertThat(profile.getImageIdentifier()).isEqualTo(BOB.imageIdentifier);
        });
    }

    @Test
    void 프로필_조회_시_게시물_수를_포함한다() {
        // given
        final User user = entityManager.merge(BOB.생성());
        entityManager.persist(POST_1.생성(user));
        entityManager.persist(POST_2.생성(user));

        // when
        final Profile profile = profileDao.getById(user.getId(), user.getId());

        // then
        assertThat(profile.getPostCount()).isEqualTo(2);
    }

    @Test
    void 프로필_조회_시_나와의_관계를_포함한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        entityManager.persist(new Follow(bob.getId(), alice.getId()));
        entityManager.persist(new Block(bob.getId(), charlie.getId()));

        // when
        final Profile aliceProfile = profileDao.getById(bob.getId(), alice.getId());
        final Profile charlieProfile = profileDao.getById(bob.getId(), charlie.getId());

        // then
        assertAll(() -> {
            assertThat(aliceProfile.isFollowing()).isTrue();
            assertThat(aliceProfile.isBlocking()).isFalse();

            assertThat(charlieProfile.isFollowing()).isFalse();
            assertThat(charlieProfile.isBlocking()).isTrue();
        });
    }

    @Test
    void 컬렉션으로_프로필_리스트를_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        final List<Long> userIds = List.of(bob.getId(), alice.getId(), charlie.getId());

        // when
        final List<Profile> profiles = profileDao.findAllByUserIdIn(bob.getId(), userIds);

        // then
        assertThat(profiles).hasSize(userIds.size());
    }

    @Test
    void 컬렉션으로_프로필_맵을_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        final List<Long> userIds = List.of(bob.getId(), alice.getId(), charlie.getId());

        // when
        final Map<Long, Profile> profiles = profileDao.findMapByUserIdIn(bob.getId(), userIds);

        // then
        assertAll(() -> {
            assertThat(profiles).hasSize(userIds.size());
            assertThat(profiles).containsOnlyKeys(userIds);
        });
    }
}
