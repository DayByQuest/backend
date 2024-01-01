package daybyquest.like.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThatCode;

import daybyquest.like.domain.PostLike;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeletePostLikeServiceTest extends ServiceTest {

    @Autowired
    private DeletePostLikeService deletePostLikeService;

    @Test
    void 좋아요를_취소한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postLikes.save(new PostLike(aliceId, postId));

        // when & then
        assertThatCode(() -> deletePostLikeService.invoke(aliceId, postId))
                .doesNotThrowAnyException();
    }
}
