package daybyquest.post.domain;


import static daybyquest.support.fixture.PostFixtures.POST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.NotExistPostException;
import daybyquest.user.domain.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostsTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private Users users;

    @InjectMocks
    private Posts posts;

    @Test
    void 사용자_ID를_검증하고_게시물을_저장한다() {
        // given
        final Long aliceId = 1L;
        given(postRepository.save(any(Post.class))).willReturn(POST_1.생성(aliceId));

        // when
        posts.save(POST_1.생성(1L));

        // then
        assertAll(() -> {
            then(users).should().validateExistentById(aliceId);
            then(postRepository).should().save(any(Post.class));
        });
    }

    @Test
    void ID를_통해_게시물을_조회한다() {
        // given
        final Long aliceId = 1L;
        final Long postId = 1L;
        final Post post = POST_1.생성(postId, aliceId, null);
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        // when
        final Post actual = posts.getById(postId);

        // then
        assertAll(() -> {
            then(postRepository).should().findById(aliceId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(post);
        });
    }

    @Test
    void ID를_통한_조회_시_게시물이_없으면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> posts.getById(1L))
                .isInstanceOf(NotExistPostException.class);
    }

    @Test
    void 게시물_ID_존재_여부를_검증한다() {
        // given
        final Long postId = 1L;
        given(postRepository.existsById(postId)).willReturn(true);

        // when
        posts.validateExistentById(postId);

        // then
        then(postRepository).should().existsById(postId);
    }
}
