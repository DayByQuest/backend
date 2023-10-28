package daybyquest.like.application;

import daybyquest.like.domain.PostDislike;
import daybyquest.like.domain.PostDislikedEvent;
import daybyquest.like.domain.PostDislikes;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavePostDislikeService {

    private final PostDislikes postDislikes;

    private final ApplicationEventPublisher publisher;

    public SavePostDislikeService(final PostDislikes postDislikes,
            final ApplicationEventPublisher publisher) {
        this.postDislikes = postDislikes;
        this.publisher = publisher;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        final PostDislike postDislike = new PostDislike(postId, loginId);
        postDislikes.save(postDislike);
        publisher.publishEvent(new PostDislikedEvent(loginId, postId));
    }
}
