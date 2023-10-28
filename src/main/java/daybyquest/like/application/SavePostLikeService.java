package daybyquest.like.application;

import daybyquest.like.domain.PostLike;
import daybyquest.like.domain.PostLikedEvent;
import daybyquest.like.domain.PostLikes;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavePostLikeService {

    private final PostLikes postLikes;

    private final ApplicationEventPublisher publisher;

    public SavePostLikeService(final PostLikes postLikes, final ApplicationEventPublisher publisher) {
        this.postLikes = postLikes;
        this.publisher = publisher;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        final PostLike postLike = new PostLike(postId, loginId);
        postLikes.save(postLike);
        publisher.publishEvent(new PostLikedEvent(loginId, postId));
    }
}
