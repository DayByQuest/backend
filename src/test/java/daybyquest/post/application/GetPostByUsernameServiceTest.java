package daybyquest.post.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.post.dto.response.PostResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPostByUsernameServiceTest extends ServiceTest {

    @Autowired
    private GetPostByUsernameService getPostByUsernameService;

    @Test
    void 퀘스트로_게시물을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();

        final List<Long> actual = List.of(posts.save(POST_1.생성(aliceId)).getId(),
                posts.save(POST_1.생성(aliceId)).getId(),
                posts.save(POST_1.생성(aliceId)).getId());
        posts.save(POST_1.생성(bobId));

        // when
        final List<Long> expected = getPostByUsernameService.invoke(aliceId, ALICE.username, 페이지()).posts()
                .stream().map(PostResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
