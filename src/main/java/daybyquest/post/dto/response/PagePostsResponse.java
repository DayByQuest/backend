package daybyquest.post.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PagePostsResponse {

    private List<PostResponse> posts;

    private Long lastId;

    public PagePostsResponse(final List<PostResponse> posts, final Long lastId) {
        this.posts = posts;
        this.lastId = lastId;
    }
}
