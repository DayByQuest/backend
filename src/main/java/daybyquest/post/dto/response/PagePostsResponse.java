package daybyquest.post.dto.response;

import java.util.List;

public record PagePostsResponse(List<PostResponse> posts, Long lastId) {

}
