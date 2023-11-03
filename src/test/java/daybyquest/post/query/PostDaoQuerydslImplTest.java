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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(PostDaoQuerydslImpl.class)
public class PostDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private PostDaoQuerydslImpl postDao;

    @Test
    void 게시물_ID로_데이터를_조회한다() {
        // given
        final User user = 저장한다(BOB.생성());
        final Post post = 저장한다(POST_1.생성(user));

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
        final User user = 저장한다(BOB.생성());
        final Post post = 저장한다(POST_WITH_3_IMAGES.생성(user));

        // when
        final PostData postData = postDao.getByPostId(user.getId(), post.getId());

        // then
        assertThat(postData.getImages()).containsExactlyInAnyOrderElementsOf(POST_WITH_3_IMAGES.사진_목록());
    }

    @Test
    void 팔로잉_목록의_게시물_ID_목록을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        저장한다(POST_1.생성(alice));
        저장한다(POST_2.생성(charlie));
        저장한다(POST_3.생성(charlie));
        저장한다(new Follow(bob.getId(), alice.getId()));
        저장한다(new Follow(bob.getId(), charlie.getId()));

        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = postDao.findPostIdsFromFollowings(bob.getId(), page);

        // then
        assertThat(ids.getIds()).hasSize(3);
    }

    @Test
    void 사용자_ID를_통해_업로드한_게시물_ID_목록을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        저장한다(POST_1.생성(bob));
        저장한다(POST_2.생성(bob));
        저장한다(POST_3.생성(bob));

        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = postDao.findPostIdsByUserId(bob.getId(), bob.getId(), page);

        // then
        assertThat(ids.getIds()).hasSize(3);
    }

    @Test
    void 컬렉션으로_게시물_리스트를_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final Post post1 = 저장한다(POST_1.생성(bob));
        final Post post2 = 저장한다(POST_2.생성(bob));
        final Post post3 = 저장한다(POST_3.생성(bob));
        final List<Long> ids = List.of(post1.getId(), post2.getId(), post3.getId());

        // when
        final List<PostData> postData = postDao.findAllByIdIn(bob.getId(), ids);

        // then
        assertThat(postData).hasSize(3);
    }
}
