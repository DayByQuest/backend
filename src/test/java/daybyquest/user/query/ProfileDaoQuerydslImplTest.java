package daybyquest.user.query;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DAVID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.NotExistUserException;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.relation.domain.Block;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(ProfileDaoQuerydslImpl.class)
public class ProfileDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private ProfileDaoQuerydslImpl profileDao;

    @Nested
    class 사용자_이름으로_프로필_조회 {

        @Test
        void 사용자_이름으로_프로필을_조회한다() {
            // given
            final User user = 저장한다(BOB.생성());

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
        void 게시물_수를_포함한다() {
            // given
            final User user = 저장한다(BOB.생성());
            저장한다(POST_1.생성(user));
            저장한다(POST_2.생성(user));

            // when
            final Profile profile = profileDao.getByUsername(user.getId(), user.getUsername());

            // then
            assertThat(profile.getPostCount()).isEqualTo(2);
        }

        @Test
        void 나와의_관계를_포함한다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            final User charlie = 저장한다(CHARLIE.생성());
            저장한다(new Follow(bob.getId(), alice.getId()));
            저장한다(new Block(bob.getId(), charlie.getId()));

            // when
            final Profile aliceProfile = profileDao.getByUsername(bob.getId(), alice.getUsername());
            final Profile charlieProfile = profileDao.getByUsername(bob.getId(), charlie.getUsername());

            // then
            assertAll(() -> {
                assertThat(aliceProfile.isFollowing()).isTrue();
                assertThat(aliceProfile.isBlocking()).isFalse();

                assertThat(charlieProfile.isFollowing()).isFalse();
                assertThat(charlieProfile.isBlocking()).isTrue();
            });
        }

        @Test
        void 팔로잉과_팔로워_수를_포함하지_않는다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            저장한다(new Follow(bob.getId(), alice.getId()));
            저장한다(new Follow(alice.getId(), bob.getId()));

            // when
            final Profile profile = profileDao.getById(bob.getId(), bob.getId());

            // then
            assertAll(() -> {
                assertThat(profile.getFollowingCount()).isEqualTo(0);
                assertThat(profile.getFollowerCount()).isEqualTo(0);
            });
        }
    }

    @Nested
    class 사용자_ID로_프로필_조회 {

        @Test
        void 사용자_ID로_프로필을_조회한다() {
            // given
            final User user = 저장한다(BOB.생성());

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
        void 게시물_수를_포함한다() {
            // given
            final User user = 저장한다(BOB.생성());
            저장한다(POST_1.생성(user));
            저장한다(POST_2.생성(user));

            // when
            final Profile profile = profileDao.getById(user.getId(), user.getId());

            // then
            assertThat(profile.getPostCount()).isEqualTo(2);
        }

        @Test
        void 나와의_관계를_포함한다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            final User charlie = 저장한다(CHARLIE.생성());
            저장한다(new Follow(bob.getId(), alice.getId()));
            저장한다(new Block(bob.getId(), charlie.getId()));

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
        void 팔로잉과_팔로워_수를_포함하지_않는다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            저장한다(new Follow(bob.getId(), alice.getId()));
            저장한다(new Follow(alice.getId(), bob.getId()));

            // when
            final Profile profile = profileDao.getByUsername(bob.getId(), bob.getUsername());

            // then
            assertAll(() -> {
                assertThat(profile.getFollowingCount()).isEqualTo(0);
                assertThat(profile.getFollowerCount()).isEqualTo(0);
            });
        }
    }

    @Nested
    class 내_프로필_조회 {

        @Test
        void 내_프로필을_조회한다() {
            // given
            final User user = 저장한다(BOB.생성());

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
        void 팔로잉_수를_포함한다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            final User charlie = 저장한다(CHARLIE.생성());
            저장한다(new Follow(bob.getId(), alice.getId()));
            저장한다(new Follow(bob.getId(), charlie.getId()));

            // when
            final Profile profile = profileDao.getMine(bob.getId());

            // then
            assertAll(() -> {
                assertThat(profile.getFollowingCount()).isEqualTo(2);
                assertThat(profile.getFollowerCount()).isEqualTo(0);
            });
        }

        @Test
        void 팔로워_수를_포함한다() {
            // given
            final User bob = 저장한다(BOB.생성());
            final User alice = 저장한다(ALICE.생성());
            final User charlie = 저장한다(CHARLIE.생성());
            저장한다(new Follow(alice.getId(), bob.getId()));
            저장한다(new Follow(charlie.getId(), bob.getId()));

            // when
            final Profile profile = profileDao.getMine(bob.getId());

            // then
            assertAll(() -> {
                assertThat(profile.getFollowingCount()).isEqualTo(0);
                assertThat(profile.getFollowerCount()).isEqualTo(2);
            });
        }
    }

    @Test
    void 사용자_이름으로_검색한다() {
        // given
        저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());
        final List<Long> expected = List.of(david.getId(), alice.getId(), charlie.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = profileDao.findIdsByUsernameLike("i", page);
        final List<Long> actual = ids.getIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 컬렉션으로_프로필_리스트를_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final List<Long> userIds = List.of(bob.getId(), alice.getId(), charlie.getId());

        // when
        final List<Profile> profiles = profileDao.findAllByUserIdIn(bob.getId(), userIds);

        // then
        assertThat(profiles).hasSize(userIds.size());
    }

    @Test
    void 컬렉션으로_프로필_맵을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
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
