package daybyquest.user.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.PostFixtures.POST_3;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.post.domain.Posts;
import daybyquest.relation.domain.Follow;
import daybyquest.relation.domain.Follows;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.response.MyProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetMyProfileServiceTest extends ServiceTest {

    @Autowired
    private GetMyProfileService getMyProfileService;

    @Autowired
    private Posts posts;

    @Autowired
    private Follows follows;

    @Test
    void 사용자_프로필을_조회한다() {
        // given
        final Long id = ALICE를_저장한다();

        // when
        final MyProfileResponse response = getMyProfileService.invoke(id);

        // then
        assertAll(() -> {
            assertThat(response.username()).isEqualTo(ALICE.username);
            assertThat(response.name()).isEqualTo(ALICE.name);
            assertThat(response.imageIdentifier()).isEqualTo(ALICE.imageIdentifier);
        });
    }

    @Test
    void 게시물_수가_함께_조회된다() {
        // given
        final Long id = ALICE를_저장한다();
        posts.save(POST_1.생성(id));
        posts.save(POST_2.생성(id));
        posts.save(POST_3.생성(id));

        // when
        final MyProfileResponse response = getMyProfileService.invoke(id);

        // then
        assertThat(response.postCount()).isEqualTo(3);
    }

    @Test
    void 팔로워_수가_함께_조회된다() {
        // given
        final Long id = ALICE를_저장한다();
        follows.save(new Follow(BOB을_저장한다(), id));
        follows.save(new Follow(CHARLIE를_저장한다(), id));
        follows.save(new Follow(DAVID를_저장한다(), id));

        // when
        final MyProfileResponse response = getMyProfileService.invoke(id);

        // then
        assertThat(response.followerCount()).isEqualTo(3);
    }

    @Test
    void 팔로잉_수가_함께_조회된다() {
        // given
        final Long id = ALICE를_저장한다();
        follows.save(new Follow(id, BOB을_저장한다()));
        follows.save(new Follow(id, CHARLIE를_저장한다()));
        follows.save(new Follow(id, DAVID를_저장한다()));

        // when
        final MyProfileResponse response = getMyProfileService.invoke(id);

        // then
        assertThat(response.followingCount()).isEqualTo(3);
    }
}
