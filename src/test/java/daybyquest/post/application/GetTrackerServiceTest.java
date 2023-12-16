package daybyquest.post.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import daybyquest.post.domain.Posts;
import daybyquest.post.dto.response.TrackerResponse;
import daybyquest.support.test.ServiceTest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetTrackerServiceTest extends ServiceTest {

    @Autowired
    private GetTrackerService getTrackerService;

    @Autowired
    private Posts posts;

    @Test
    void 트래커를_조회한다() {
        // given
        final Long id = ALICE를_저장한다();
        final LocalDateTime now = LocalDateTime.now();

        given(dataTimeProvider.getNow()).willReturn(Optional.of(now));
        posts.save(POST_1.생성(id));
        given(dataTimeProvider.getNow()).willReturn(Optional.of(now.minusDays(1)));
        posts.save(POST_1.생성(id));
        posts.save(POST_1.생성(id));
        given(dataTimeProvider.getNow()).willReturn(Optional.of(now.minusDays(3)));
        posts.save(POST_1.생성(id));
        given(dataTimeProvider.getNow()).willReturn(Optional.of(now.minusDays(4)));
        posts.save(POST_1.생성(id));

        final List<Long> expected = List.of(1L, 2L, 0L, 1L, 1L);

        // when
        final TrackerResponse response = getTrackerService.invoke(id, ALICE.username);
        final List<Long> tracker = response.tracker();

        // then
        assertThat(tracker).containsExactlyElementsOf(expected);
    }
}
