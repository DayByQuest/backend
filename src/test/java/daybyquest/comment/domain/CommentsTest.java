package daybyquest.comment.domain;

import static daybyquest.support.fixture.CommentFixtures.댓글_1;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentsTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private Users users;

    @Mock
    private Posts posts;

    @InjectMocks
    private Comments comments;

    @Test
    void 사용자와_게시물_ID를_검증하고_댓글을_저장한다() {
        // given
        final Long userId = 1L;
        final Long postId = 2L;

        // when
        comments.save(댓글_1.생성(userId, postId));

        // then
        assertAll(() -> {
            then(users).should().validateExistentById(userId);
            then(posts).should().validateExistentById(postId);
            then(commentRepository).should().save(any(Comment.class));
        });
    }
}
