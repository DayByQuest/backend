package daybyquest.like.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_LIKED_POST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.dislike.domain.PostDislike;
import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.like.domain.PostLike;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SavePostLikeServiceTest extends ServiceTest {

    @Autowired
    private SavePostLikeService savePostLikeService;

    @Test
    void 좋아요를_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();

        // when
        savePostLikeService.invoke(aliceId, postId);

        // then
        assertThatThrownBy(() -> postLikes.save(new PostLike(aliceId, postId)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_LIKED_POST.getMessage());
    }

    @Test
    void 이미_좋아요를_눌렀으면_저장할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postLikes.save(new PostLike(aliceId, postId));

        // when & then
        assertThatThrownBy(() -> savePostLikeService.invoke(aliceId, postId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_LIKED_POST.getMessage());
    }

    @Test
    void 좋아요를_누르면_관심_없음이_취소된다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(aliceId)).getId();
        postDislikes.save(new PostDislike(aliceId, postId));

        // when & then
        assertThatCode(() -> savePostLikeService.invoke(aliceId, postId))
                .doesNotThrowAnyException();
    }
}
