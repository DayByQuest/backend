package daybyquest.post.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.dto.response.PostResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.PostData;
import daybyquest.user.query.Profile;
import daybyquest.user.query.ProfileDao;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostFromFollowingService {

    private final PostDao postDao;

    private final ProfileDao profileDao;

    public GetPostFromFollowingService(final PostDao postDao, final ProfileDao profileDao) {
        this.postDao = postDao;
        this.profileDao = profileDao;
    }

    @Transactional(readOnly = true)
    public PagePostsResponse invoke(final Long loginId, final NoOffsetIdPage page) {
        final LongIdList postIds = postDao.findPostIdsFromFollowings(loginId, page);
        final List<PostData> postData = postDao.findAllByIdIn(loginId, postIds.getIds());
        final Set<Long> userIds = postData.stream().map(PostData::getUserId).collect(Collectors.toSet());
        final Map<Long, Profile> profiles = profileDao.findMapByUserIdIn(loginId, userIds);
        final List<PostResponse> responses = postData.stream()
                .map((pd) -> PostResponse.of(pd, profiles.get(pd.getUserId()))).toList();
        return new PagePostsResponse(responses, postIds.getLastId());
    }
}
