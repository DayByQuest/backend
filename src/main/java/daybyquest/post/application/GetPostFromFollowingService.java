package daybyquest.post.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.PostData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostFromFollowingService {

    private final PostDao postDao;

    private final PostResponseConverter converter;

    public GetPostFromFollowingService(final PostDao postDao, final PostResponseConverter converter) {
        this.postDao = postDao;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public PagePostsResponse invoke(final Long loginId, final NoOffsetIdPage page) {
        final LongIdList postIds = postDao.findPostIdsFromFollowings(loginId, page);
        final List<PostData> postData = postDao.findAllByIdIn(loginId, postIds.getIds());
        return new PagePostsResponse(converter.convertFromPostData(loginId, postData), postIds.getLastId());
    }
}
