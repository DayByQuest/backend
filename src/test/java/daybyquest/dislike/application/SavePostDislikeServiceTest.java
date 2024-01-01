package daybyquest.dislike.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_DISLIKED_POST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.dislike.domain.PostDislike;
import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.like.domain.PostLike;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SavePostDislikeServiceTest extends ServiceTest {

    @Autowired
    private SavePostDislikeService savePostDislikeService;

    @Test
    void 관심_없음을_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();

        // when
        savePostDislikeService.invoke(aliceId, postId);

        // then
        assertThatThrownBy(() -> postDislikes.save(new PostDislike(aliceId, postId)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_DISLIKED_POST.getMessage());
    }

    @Test
    void 이미_관심_없음을_눌렀으면_저장할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postDislikes.save(new PostDislike(aliceId, postId));

        // when & then
        assertThatThrownBy(() -> savePostDislikeService.invoke(aliceId, postId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_DISLIKED_POST.getMessage());
    }

    @Test
    void 관심_없음을_누르면_좋아요가_취소된다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postLikes.save(new PostLike(aliceId, postId));

        // when & then
        assertThatCode(() -> savePostDislikeService.invoke(aliceId, postId))
                .doesNotThrowAnyException();
    }
}
