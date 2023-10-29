package daybyquest.comment.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageCommentsResponse {

    private List<CommentResponse> comments;

    private Long lastId;

    public PageCommentsResponse(final List<CommentResponse> comments, final Long lastId) {
        this.comments = comments;
        this.lastId = lastId;
    }
}
