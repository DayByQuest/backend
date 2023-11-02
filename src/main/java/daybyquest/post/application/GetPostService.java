package daybyquest.post.application;

import daybyquest.post.dto.response.PostWithQuestResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.PostData;
import daybyquest.quest.domain.Quests;
import daybyquest.user.query.Profile;
import daybyquest.user.query.ProfileDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostService {

    private final PostDao postDao;

    private final ProfileDao profileDao;

    private final Quests quests;

    public GetPostService(final PostDao postDao, final ProfileDao profileDao, final Quests quests) {
        this.postDao = postDao;
        this.profileDao = profileDao;
        this.quests = quests;
    }

    @Transactional(readOnly = true)
    public PostWithQuestResponse invoke(final Long loginId, final Long postId) {
        final PostData postData = postDao.getByPostId(loginId, postId);
        final Profile profile = profileDao.getById(loginId, postData.getUserId());
        return PostWithQuestResponse.of(postData, profile);
    }
}
