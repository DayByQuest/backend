package daybyquest.post.query;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.PostFixtures.POST_3;
import static daybyquest.support.fixture.PostFixtures.POST_WITH_3_IMAGES;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.domain.Post;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(PostDaoQuerydslImpl.class)
public class PostDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private PostDaoQuerydslImpl postDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 게시물_ID로_데이터를_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());
        final Post post = entityManager.merge(POST_1.생성(user));

        // when
        final PostData postData = postDao.getByPostId(user.getId(), post.getId());

        // then
        assertAll(() -> {
            assertThat(postData.getContent()).isEqualTo(POST_1.content);
            assertThat(postData.getImages()).containsExactlyInAnyOrderElementsOf(POST_1.사진_목록());
            assertThat(postData.getUserId()).isEqualTo(user.getId());
        });
    }

    @Test
    void 게시물_사진이_여러장_이면_모두_함께_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());
        final Post post = entityManager.merge(POST_WITH_3_IMAGES.생성(user));

        // when
        final PostData postData = postDao.getByPostId(user.getId(), post.getId());

        // then
        assertThat(postData.getImages()).containsExactlyInAnyOrderElementsOf(POST_WITH_3_IMAGES.사진_목록());
    }

    @Test
    void 팔로잉_목록의_게시물_ID_목록을_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        entityManager.persist(POST_1.생성(alice));
        entityManager.persist(POST_2.생성(charlie));
        entityManager.persist(POST_3.생성(charlie));
        entityManager.persist(new Follow(bob.getId(), alice.getId()));
        entityManager.persist(new Follow(bob.getId(), charlie.getId()));

        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = postDao.findPostIdsFromFollowings(bob.getId(), page);

        // then
        assertThat(ids.getIds()).hasSize(3);
    }

    @Test
    void 사용자_ID를_통해_업로드한_게시물_ID_목록을_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        entityManager.persist(POST_1.생성(bob));
        entityManager.persist(POST_2.생성(bob));
        entityManager.persist(POST_3.생성(bob));

        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = postDao.findPostIdsByUserId(bob.getId(), bob.getId(), page);

        // then
        assertThat(ids.getIds()).hasSize(3);
    }

    @Test
    void 컬렉션으로_게시물_리스트를_조회한다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final Post post1 = entityManager.merge(POST_1.생성(bob));
        final Post post2 = entityManager.merge(POST_2.생성(bob));
        final Post post3 = entityManager.merge(POST_3.생성(bob));
        final List<Long> ids = List.of(post1.getId(), post2.getId(), post3.getId());

        // when
        final List<PostData> postData = postDao.findAllByIdIn(bob.getId(), ids);

        // then
        assertThat(postData).hasSize(3);
    }
}
