package daybyquest.post.dto.response;

import daybyquest.image.vo.Image;
import daybyquest.post.query.PostData;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {

    private ProfileResponse author;

    private Long id;

    private String content;

    private LocalDateTime updatedAt;

    private boolean liked;

    private List<String> images;

    public PostResponse(final ProfileResponse author, final Long id, final String content,
            final LocalDateTime updatedAt, final boolean liked,
            final List<String> images) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.images = images;
    }

    public static PostResponse of(final PostData postData, final Profile profile) {
        return new PostResponse(ProfileResponse.of(profile), postData.getId(), postData.getContent(),
                postData.getUpdatedAt(), postData.isLiked(),
                postData.getImages().stream().map(Image::getImageIdentifier).toList()
        );
    }
}
