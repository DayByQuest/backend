package daybyquest.support.fixture;

import daybyquest.comment.domain.Comment;
import daybyquest.comment.dto.response.CommentResponse;
import daybyquest.comment.query.CommentData;
import daybyquest.global.constant.TimeConstant;
import daybyquest.post.domain.Post;
import daybyquest.user.domain.User;
import daybyquest.user.query.Profile;
import org.springframework.test.util.ReflectionTestUtils;

public enum CommentFixtures {

    댓글_1("댓글 내용 1"),
    댓글_2("댓글 내용 2"),
    댓글_3("댓글 내용 3");

    public final String content;

    CommentFixtures(final String content) {
        this.content = content;
    }

    public Comment 생성(final Long id, final Long postId, final Long userId) {
        final Comment comment = new Comment(userId, postId, content);
        ReflectionTestUtils.setField(comment, "id", id);
        return comment;
    }

    public Comment 생성(final Long postId, final Long userId) {
        return 생성(null, postId, userId);
    }

    public Comment 생성(final Post post, final User user) {
        return 생성(null, post.getId(), user.getId());
    }

    public Comment 생성(final Post post) {
        return 생성(null, post.getId(), post.getUserId());
    }

    public CommentResponse 응답(final Long id, final Profile profile) {
        final CommentData commentData = new CommentData(profile.getId(), id, content, TimeConstant.EXAMPLE);
        return CommentResponse.of(commentData, profile);
    }
}
