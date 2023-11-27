package daybyquest.comment.dto.response;

import java.util.List;

public record PageCommentsResponse(List<CommentResponse> comments, Long lastId) {

}
