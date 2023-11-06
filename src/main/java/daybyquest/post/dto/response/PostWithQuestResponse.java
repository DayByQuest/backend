package daybyquest.post.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.image.domain.Image;
import daybyquest.post.domain.PostState;
import daybyquest.post.query.PostData;
import daybyquest.user.dto.response.ProfileResponse;
import daybyquest.user.query.Profile;
import java.time.LocalDateTime;
import java.util.List;

public record PostWithQuestResponse(ProfileResponse author, Long id, String content,
                                    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime updatedAt,
                                    boolean liked, List<String> imageIdentifiers,
                                    daybyquest.post.dto.response.PostWithQuestResponse.SimpleQuestResponse quest) {

    public PostWithQuestResponse(final ProfileResponse author, final Long id, final String content,
            final LocalDateTime updatedAt, final boolean liked,
            final List<String> imageIdentifiers, final SimpleQuestResponse quest) {
        this.author = author;
        this.id = id;
        this.content = content;
        this.updatedAt = updatedAt;
        this.liked = liked;
        this.imageIdentifiers = imageIdentifiers;
        if (quest.questId() == null) {
            this.quest = null;
            return;
        }
        this.quest = quest;
    }

    public static PostWithQuestResponse of(final PostData postData, final Profile profile) {
        return new PostWithQuestResponse(
                ProfileResponse.of(profile),
                postData.getId(), postData.getContent(), postData.getUpdatedAt(), postData.isLiked(),
                postData.getImages().stream().map(Image::getIdentifier).toList(),
                new SimpleQuestResponse(postData.getQuestId(), postData.getQuestTitle(), postData.getState())
        );
    }

    public record SimpleQuestResponse(Long questId, String title, PostState state) {

    }
}
