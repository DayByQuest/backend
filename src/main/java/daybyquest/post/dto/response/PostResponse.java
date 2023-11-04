package daybyquest.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.image.domain.Image;
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

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    private boolean liked;

    private List<String> imageIdentifiers;

    public PostResponse(final ProfileResponse author, final Long id, final String content,
            final LocalDateTime updatedAt, final boolean liked,
            final List<String> imageIdentifiers) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.imageIdentifiers = imageIdentifiers;
    }

    public static PostResponse of(final PostData postData, final Profile profile) {
        return new PostResponse(ProfileResponse.of(profile), postData.getId(), postData.getContent(),
                postData.getUpdatedAt(), postData.isLiked(),
                postData.getImages().stream().map(Image::getIdentifier).toList()
        );
    }
}
