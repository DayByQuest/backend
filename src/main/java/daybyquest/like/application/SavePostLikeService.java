package daybyquest.like.application;

import daybyquest.like.domain.PostLike;
import daybyquest.like.domain.PostLikes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavePostLikeService {

    private final PostLikes postLikes;

    public SavePostLikeService(final PostLikes postLikes) {
        this.postLikes = postLikes;
    }

    @Transactional
    public void invoke(final Long loginId, final Long postId) {
        final PostLike postLike = new PostLike(postId, loginId);
        postLikes.save(postLike);
    }
}
