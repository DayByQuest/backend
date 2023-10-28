package daybyquest.post.application;

import daybyquest.post.domain.PostSwipedEvent;
import daybyquest.post.domain.Posts;
import daybyquest.user.domain.Users;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SwipePostService {

    private final Users users;

    private final Posts posts;

    private final ApplicationEventPublisher publisher;

    public SwipePostService(final Users users, final Posts posts, final ApplicationEventPublisher publisher) {
        this.users = users;
        this.posts = posts;
        this.publisher = publisher;
    }

    @Transactional(readOnly = true)
    public void invoke(final Long loginId, final Long postId) {
        users.validateExistentById(loginId);
        posts.validateExistentById(postId);
        publisher.publishEvent(new PostSwipedEvent(loginId, postId));
    }
}
