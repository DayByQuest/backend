package daybyquest.post.application;

import java.util.List;

public interface PostClient {

    void requestJudge(final Long postId, final String label, final List<String> identifiers);
}
