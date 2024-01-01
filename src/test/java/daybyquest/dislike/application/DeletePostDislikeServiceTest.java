package daybyquest.dislike.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThatCode;

import daybyquest.dislike.domain.PostDislike;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeletePostDislikeServiceTest extends ServiceTest {

    @Autowired
    private DeletePostDislikeService deletePostDislikeService;

    @Test
    void 관심_없음을_취소한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postDislikes.save(new PostDislike(aliceId, postId));

        // when & then
        assertThatCode(() -> deletePostDislikeService.invoke(aliceId, postId))
                .doesNotThrowAnyException();
    }
}
