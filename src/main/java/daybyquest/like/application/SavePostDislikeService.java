package daybyquest.like.application;

import daybyquest.like.domain.PostDislike;
import daybyquest.like.domain.PostDislikes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavePostDislikeService {

    private final PostDislikes postDislikes;

    public SavePostDislikeService(final PostDislikes postDislikes) {
        this.postDislikes = postDislikes;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        final PostDislike postDislike = new PostDislike(postId, loginId);
        postDislikes.save(postDislike);
    }
}
