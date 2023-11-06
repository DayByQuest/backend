package daybyquest.post.application;

import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import daybyquest.post.dto.response.TrackerResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.SimplePostData;
import daybyquest.user.domain.Users;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetTrackerService {

    private static final long TRACKER_DAYS = 60L;

    private final Users users;

    private final PostDao postDao;

    public GetTrackerService(final Users users, final PostDao postDao) {
        this.users = users;
        this.postDao = postDao;
    }

    @Transactional(readOnly = true)
    public TrackerResponse invoke(final Long loginId, final String username) {
        final Long userId = users.getUserIdByUsername(username);
        final List<SimplePostData> simplePostData = postDao
                .findAllBySuccessAndUploadedAtAfter(userId, now().minusDays(TRACKER_DAYS));
        return new TrackerResponse(calculateTracker(simplePostData));
    }

    private List<Long> calculateTracker(final List<SimplePostData> simplePostData) {
        final Map<LocalDate, Long> counts = simplePostData.stream()
                .collect(groupingBy((time) -> time.uploadedAt().toLocalDate(), counting()));

        return Stream.iterate(LocalDate.now(), date -> date.minusDays(1))
                .limit(TRACKER_DAYS)
                .map(time -> counts.getOrDefault(time, 0L)
                ).toList();
    }
}
