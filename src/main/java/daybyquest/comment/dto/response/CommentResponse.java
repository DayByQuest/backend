package daybyquest.comment.dto.response;

import daybyquest.comment.query.CommentData;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;

public record CommentResponse(ProfileResponse author, Long id, String content, LocalDateTime updatedAt) {

    public static CommentResponse of(final CommentData commentData, final Profile profile) {
        return new CommentResponse(ProfileResponse.of(profile), commentData.getId(), commentData.getContent(),
                commentData.getUpdatedAt());
    }
}
