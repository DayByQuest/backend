package daybyquest.post.application;

import daybyquest.post.dto.response.PostResponse;
import daybyquest.post.query.PostData;
import daybyquest.user.query.Profile;
import daybyquest.user.query.ProfileDao;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PostResponseConverter {

    private final ProfileDao profileDao;

    public PostResponseConverter(final ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    public List<PostResponse> convertFromPostData(final Long loginId, final List<PostData> postData) {
        final Set<Long> userIds = postData.stream().map(PostData::getUserId).collect(Collectors.toSet());
        final Map<Long, Profile> profiles = profileDao.findMapByUserIdIn(loginId, userIds);
        return postData.stream()
                .map((pd) -> PostResponse.of(pd, profiles.get(pd.getUserId()))).toList();
    }
}
