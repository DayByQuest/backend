package daybyquest.post.query;

public interface PostDao {

    PostData getByPostId(final Long userId, final Long postId);
}
