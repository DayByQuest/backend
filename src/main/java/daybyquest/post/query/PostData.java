package daybyquest.post.query;

import daybyquest.image.domain.Image;
import daybyquest.post.domain.PostState;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class PostData {

    private final Long userId;

    private final Long id;

    private final String content;

    private final LocalDateTime updatedAt;

    private final boolean liked;

    private List<Image> images;

    private final Long questId;

    private final String questTitle;

    private final PostState state;

    public PostData(final Long userId, final Long id, final String content, final LocalDateTime updatedAt,
            final boolean liked, final Long questId, final String questTitle, final PostState state) {
        this.userId = userId;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.questId = questId;
        this.questTitle = questTitle;
        this.state = state;
    }

    public void setImages(final List<Image> images) {
        this.images = images;
    }
}
