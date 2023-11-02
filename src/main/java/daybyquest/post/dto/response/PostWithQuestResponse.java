package daybyquest.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.image.vo.Image;
import daybyquest.post.domain.PostState;
import daybyquest.post.query.PostData;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostWithQuestResponse {

    private ProfileResponse author;

    private Long id;

    private String content;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;

    private boolean liked;

    private List<String> imageIdentifiers;

    private SimpleQuestResponse quest;

    public PostWithQuestResponse(final ProfileResponse author, final Long id, final String content,
            final LocalDateTime updatedAt, final boolean liked,
            final List<String> imageIdentifiers, final SimpleQuestResponse quest) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.imageIdentifiers = imageIdentifiers;
        this.quest = quest;
        if (quest.getQuestId() == null) {
            this.quest = null;
        }
    }

    public static PostWithQuestResponse of(final PostData postData, final Profile profile) {
        return new PostWithQuestResponse(
                ProfileResponse.of(profile),
                postData.getId(), postData.getContent(), postData.getUpdatedAt(), postData.isLiked(),
                postData.getImages().stream().map(Image::getImageIdentifier).toList(),
                new SimpleQuestResponse(postData.getQuestId(), postData.getQuestTitle(), postData.getState())
        );
    }

    @Getter
    @NoArgsConstructor
    private static class SimpleQuestResponse {

        private Long questId;

        private String title;

        private PostState state;

        public SimpleQuestResponse(final Long questId, final String title, final PostState state) {
            this.questId = questId;
            this.title = title;
            this.state = state;
        }
    }
}
