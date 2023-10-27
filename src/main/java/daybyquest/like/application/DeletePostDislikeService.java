package daybyquest.like.application;

import daybyquest.like.domain.PostDislikes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePostDislikeService {

    private final PostDislikes postDislikes;

    public DeletePostDislikeService(final PostDislikes postDislikes) {
        this.postDislikes = postDislikes;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        postDislikes.deleteByUserIdAndPostId(loginId, postId);
    }
}
