package daybyquest.comment.application;

import static daybyquest.support.fixture.CommentFixtures.댓글_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.comment.domain.Comment;
import daybyquest.comment.dto.request.SaveCommentRequest;
import daybyquest.support.fixture.PostFixtures;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class SaveCommentServiceTest extends ServiceTest {

    @Autowired
    private SaveCommentService saveCommentService;

    @Test
    void 댓글을_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long postId = posts.save(PostFixtures.POST_1.생성(aliceId)).getId();
        final SaveCommentRequest request = 댓글_생성_요청(댓글_1.생성(postId, aliceId));

        // when
        final Long commentId = saveCommentService.invoke(aliceId, postId, request);
        final Comment comment = comments.getById(commentId);

        // then
        assertAll(() -> {
            assertThat(comment.getUserId()).isEqualTo(aliceId);
            assertThat(comment.getPostId()).isEqualTo(postId);
            assertThat(comment.getContent()).isEqualTo(댓글_1.content);
        });
    }

    private SaveCommentRequest 댓글_생성_요청(final Comment comment) {
        final SaveCommentRequest request = new SaveCommentRequest();
        ReflectionTestUtils.setField(request, "content", comment.getContent());
        return request;
    }
}
