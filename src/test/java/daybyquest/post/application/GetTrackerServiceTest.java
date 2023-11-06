package daybyquest.post.application;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import daybyquest.post.dto.response.TrackerResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.SimplePostData;
import daybyquest.user.domain.Users;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetTrackerServiceTest {

    private final Users users = BDDMockito.mock(Users.class);

    private final PostDao postDao = BDDMockito.mock(PostDao.class);

    private final GetTrackerService getTrackerService = new GetTrackerService(users, postDao, 5L);

    @Test
    void 트래커를_조회한다() {
        // given
        final Long loginId = 1L;
        final Long aliceId = 2L;
        final LocalDateTime now = LocalDateTime.now();
        final List<SimplePostData> simplePostData = List.of(
                new SimplePostData(1L, now),
                new SimplePostData(2L, now.minusDays(1)),
                new SimplePostData(3L, now.minusDays(1)),
                new SimplePostData(4L, now.minusDays(3)),
                new SimplePostData(4L, now.minusDays(4))
        );
        final List<Long> expected = List.of(1L, 2L, 0L, 1L, 1L);

        given(users.getUserIdByUsername(ALICE.username)).willReturn(aliceId);
        given(postDao.findAllBySuccessAndUploadedAtAfter(eq(aliceId), any())).willReturn(simplePostData);

        // when
        final TrackerResponse response = getTrackerService.invoke(loginId, ALICE.username);
        final List<Long> tracker = response.tracker();

        // then
        assertThat(tracker).containsExactlyElementsOf(expected);
    }
}
