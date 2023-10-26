package daybyquest.post.application;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.post.dto.response.PagePostsResponse;
import daybyquest.post.query.PostDao;
import daybyquest.post.query.PostData;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetPostByUsernameService {

    private final PostDao postDao;

    private final Users users;

    private final PostResponseConverter converter;

    public GetPostByUsernameService(final PostDao postDao, final Users users,
            final PostResponseConverter converter) {
        this.postDao = postDao;
        this.users = users;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public PagePostsResponse invoke(final Long loginId, final String username, final NoOffsetIdPage page) {
        final Long targetId = users.getUserIdByUsername(username);
        final LongIdList postIds = postDao.findPostIdsByUserId(loginId, targetId, page);
        final List<PostData> postData = postDao.findAllByIdIn(loginId, postIds.getIds());
        return new PagePostsResponse(converter.convertFromPostData(loginId, postData), postIds.getLastId());
    }
}
