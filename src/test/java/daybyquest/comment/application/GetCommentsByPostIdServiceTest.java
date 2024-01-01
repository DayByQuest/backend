package daybyquest.comment.application;

import static daybyquest.support.fixture.CommentFixtures.댓글_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.comment.dto.response.CommentResponse;
import daybyquest.comment.dto.response.PageCommentsResponse;
import daybyquest.support.fixture.PostFixtures;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetCommentsByPostIdServiceTest extends ServiceTest {

    @Autowired
    private GetCommentsByPostIdService getCommentsByPostIdService;

    @Test
    void 게시물_ID를_통해_댓글_목록을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();

        final Long post1Id = posts.save(PostFixtures.POST_1.생성(aliceId)).getId();
        final Long post2Id = posts.save(PostFixtures.POST_2.생성(aliceId)).getId();

        final List<Long> expected = List.of(comments.save(댓글_1.생성(post1Id, aliceId)).getId(),
                comments.save(댓글_1.생성(post1Id, bobId)).getId());
        comments.save(댓글_1.생성(post2Id, aliceId));

        // when
        final PageCommentsResponse response = getCommentsByPostIdService.invoke(aliceId, post1Id, 페이지());
        final List<Long> actual = response.comments().stream().map(CommentResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
