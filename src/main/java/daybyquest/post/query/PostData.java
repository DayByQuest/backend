package daybyquest.post.query;

import daybyquest.image.vo.Image;
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

    public PostData(final Long userId, final Long id, final String content, final LocalDateTime updatedAt,
            final boolean liked) {
        this.userId = userId;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
    }

    public void setImages(final List<Image> images) {
        this.images = images;
    }
}
