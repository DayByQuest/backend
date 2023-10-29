package daybyquest.comment.query;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentData {

    private final Long userId;

    private final Long id;

    private final String content;

    private final LocalDateTime updatedAt;

    public CommentData(final Long userId, final Long id, final String content,
            final LocalDateTime updatedAt) {
        this.userId = userId;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
    }
}
