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
import daybyquest.user.dto.response.ProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetProfileByUsernameServiceTest extends ServiceTest {

    @Autowired
    private GetProfileByUsernameService getProfileByUsernameService;

    @Autowired
    private Follows follows;

    @Autowired
    private Posts posts;

    @Test
    void 사용자_프로필을_조회한다() {
        // given
        final Long id = BOB을_저장한다();
        ALICE를_저장한다();

        // when
        final ProfileResponse response = getProfileByUsernameService.invoke(id, ALICE.username);

        // then
        assertAll(() -> {
            assertThat(response.username()).isEqualTo(ALICE.username);
            assertThat(response.name()).isEqualTo(ALICE.name);
            assertThat(response.imageIdentifier()).isEqualTo(ALICE.imageIdentifier);
        });
    }

    @Test
    void 팔로우_여부가_함께_조회된다() {
        // given
        final Long id = BOB을_저장한다();
        final Long aliceId = ALICE를_저장한다();
        follows.save(new Follow(id, aliceId));

        // when
        final ProfileResponse response = getProfileByUsernameService.invoke(id, ALICE.username);

        // then
        assertThat(response.following()).isTrue();
    }

    @Test
    void 게시물_수가_함께_조회된다() {
        // given
        final Long id = BOB을_저장한다();
        final Long aliceId = ALICE를_저장한다();
        posts.save(POST_1.생성(aliceId));
        posts.save(POST_2.생성(aliceId));
        posts.save(POST_3.생성(aliceId));

        // when
        final ProfileResponse response = getProfileByUsernameService.invoke(id, ALICE.username);

        // then
        assertThat(response.postCount()).isEqualTo(3);
    }
}
