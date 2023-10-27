package daybyquest.like.application;

import daybyquest.like.domain.PostLikes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeletePostLikeService {

    private final PostLikes postLikes;

    public DeletePostLikeService(final PostLikes postLikes) {
        this.postLikes = postLikes;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        postLikes.deleteByUserIdAndPostId(loginId, postId);
    }
}
