package daybyquest.dislike.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostDislikesTest {

    @Mock
    private PostDislikeRepository postDislikeRepository;

    @Mock
    private Users users;

    @Mock
    private Posts posts;

    @InjectMocks
    private PostDislikes postDislikes;

    @Test
    void 사용자와_게시물_ID를_검증하고_관심없음을_저장한다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when
        postDislikes.save(new PostDislike(userId, postId));

        // then
        assertAll(() -> {
            verify(users).validateExistentById(userId);
            verify(posts).validateExistentById(postId);
            verify(postDislikeRepository).save(any(PostDislike.class));
        });
    }

    @Test
    void 저장_시_이미_관심없음_중이라면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;
        given(postDislikeRepository.existsByUserIdAndPostId(userId, postId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> postDislikes.save(new PostDislike(userId, postId)))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자와_게시물_ID를_검증하고_관심없음을_삭제한다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;
        given(postDislikeRepository.existsByUserIdAndPostId(userId, postId)).willReturn(true);

        // when
        postDislikes.deleteByUserIdAndPostId(userId, postId);

        // then
        verify(postDislikeRepository).deleteByUserIdAndPostId(userId, postId);
    }

    @Test
    void 삭제_시_관심없음이_없다면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when & then
        assertThatThrownBy(() -> postDislikes.deleteByUserIdAndPostId(userId, postId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 검증_없이_삭제_시_존재하지_않아도_예외를_던지지_않는다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when & then
        assertAll(() -> {
            assertThatCode(() -> postDislikes.deleteByUserIdAndPostIdWithoutValidation(userId, postId))
                    .doesNotThrowAnyException();
            verify(postDislikeRepository).deleteByUserIdAndPostId(userId, postId);
        });
    }
}
