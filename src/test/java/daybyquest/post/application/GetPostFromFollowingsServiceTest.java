package daybyquest.post.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.post.dto.response.PostResponse;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPostFromFollowingsServiceTest extends ServiceTest {

    @Autowired
    private GetPostFromFollowingService getPostFromFollowingService;

    @Test
    void 팔로잉_게시물을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        final Long charlieId = CHARLIE를_저장한다();
        final Long davidId = DAVID를_저장한다();

        follows.save(new Follow(aliceId, bobId));
        follows.save(new Follow(aliceId, charlieId));

        final List<Long> actual = List.of(posts.save(POST_1.생성(charlieId)).getId(),
                posts.save(POST_1.생성(charlieId)).getId(),
                posts.save(POST_1.생성(bobId)).getId());
        posts.save(POST_1.생성(aliceId));
        posts.save(POST_1.생성(davidId));

        // when
        final List<Long> expected = getPostFromFollowingService.invoke(aliceId, 페이지()).posts()
                .stream().map(PostResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
