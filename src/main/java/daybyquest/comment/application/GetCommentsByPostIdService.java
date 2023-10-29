package daybyquest.comment.application;

import daybyquest.comment.dto.response.PageCommentsResponse;
import daybyquest.comment.query.CommentDao;
import daybyquest.comment.query.CommentData;
import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetCommentsByPostIdService {

    private final CommentDao commentDao;

    private final CommentResponseConverter converter;

    public GetCommentsByPostIdService(final CommentDao commentDao, final CommentResponseConverter converter) {
        this.commentDao = commentDao;
        this.converter = converter;
    }

    @Transactional(readOnly = true)
    public PageCommentsResponse invoke(final Long loginId, final Long postId, final NoOffsetIdPage page) {
        final LongIdList commentIds = commentDao.findIdsByPostId(loginId, postId, page);
        final List<CommentData> commentData = commentDao.findAllByIdIn(loginId, commentIds.getIds());
        return new PageCommentsResponse(converter.convertFromCommentData(loginId, commentData),
                commentIds.getLastId());
    }
}
