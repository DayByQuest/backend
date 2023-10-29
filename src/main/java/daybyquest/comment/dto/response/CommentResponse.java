package daybyquest.comment.dto.response;

import daybyquest.comment.query.CommentData;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {

    private ProfileResponse author;

    private Long id;

    private String content;

    private LocalDateTime updatedAt;

    public CommentResponse(final ProfileResponse author, final Long id, final String content,
            final LocalDateTime updatedAt) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
    }

    public static CommentResponse of(final CommentData commentData, final Profile profile) {
        return new CommentResponse(ProfileResponse.of(profile), commentData.getId(), commentData.getContent(),
                commentData.getUpdatedAt());
    }
}
