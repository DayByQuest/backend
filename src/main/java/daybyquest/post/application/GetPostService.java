package daybyquest.post.application;

import daybyquest.post.dto.response.PostWithQuestResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.PostData;
import daybyquest.user.query.Profile;
import daybyquest.user.query.ProfileDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostService {

    private final PostDao postDao;

    private final ProfileDao profileDao;

    public GetPostService(final PostDao postDao, final ProfileDao profileDao) {
        this.postDao = postDao;
        this.profileDao = profileDao;
    }

    @Transactional(readOnly = true)
    public PostWithQuestResponse invoke(final Long loginId, final Long postId) {
        final PostData postData = postDao.getByPostId(loginId, postId);
        final Profile profile = profileDao.getById(loginId, postData.getUserId());
        return PostWithQuestResponse.of(postData, profile);
    }
}
