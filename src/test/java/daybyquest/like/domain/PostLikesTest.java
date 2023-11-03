package daybyquest.like.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostLikesTest {


    @Mock
    private PostLikeRepository postLikeRepository;

    @Mock
    private Users users;

    @Mock
    private Posts posts;

    @InjectMocks
    private PostLikes postLikes;

    @Test
    void 사용자와_게시물_ID를_검증하고_좋아요를_저장한다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when
        postLikes.save(new PostLike(userId, postId));

        // then
        assertAll(() -> {
            then(users).should().validateExistentById(userId);
            then(posts).should().validateExistentById(postId);
            then(postLikeRepository).should().save(any(PostLike.class));
        });
    }

    @Test
    void 저장_시_이미_좋아요_중이라면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;
        given(postLikeRepository.existsByUserIdAndPostId(userId, postId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> postLikes.save(new PostLike(userId, postId)))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자와_게시물_ID를_검증하고_좋아요를_삭제한다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;
        given(postLikeRepository.existsByUserIdAndPostId(userId, postId)).willReturn(true);

        // when
        postLikes.deleteByUserIdAndPostId(userId, postId);

        // then
        then(postLikeRepository).should().deleteByUserIdAndPostId(userId, postId);
    }

    @Test
    void 삭제_시_좋아요가_없다면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when & then
        assertThatThrownBy(() -> postLikes.deleteByUserIdAndPostId(userId, postId))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 검증_없이_삭제_시_존재하지_않아도_예외를_던지지_않는다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when & then
        assertAll(() -> {
            assertThatCode(() -> postLikes.deleteByUserIdAndPostIdWithoutValidation(userId, postId))
                    .doesNotThrowAnyException();
            then(postLikeRepository).should().deleteByUserIdAndPostId(userId, postId);
        });
    }
}
