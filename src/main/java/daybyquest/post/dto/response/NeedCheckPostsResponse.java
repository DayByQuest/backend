package daybyquest.post.dto.response;

import java.util.List;

public record NeedCheckPostsResponse(List<String> images, List<SimplePostResponse> posts) {

}
