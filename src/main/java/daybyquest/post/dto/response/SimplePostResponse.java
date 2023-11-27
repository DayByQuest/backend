package daybyquest.post.dto.response;

import daybyquest.image.domain.Image;
import daybyquest.post.domain.Post;
import java.util.List;

public record SimplePostResponse(Long id, List<String> imageIdentifiers) {

    public static SimplePostResponse of(final Post post) {
        return new SimplePostResponse(
                post.getId(), post.getImages().stream().map(Image::getIdentifier).toList()
        );
    }
}
