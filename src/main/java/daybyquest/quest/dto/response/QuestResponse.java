package daybyquest.quest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.quest.query.QuestData;
import java.time.LocalDateTime;

public record QuestResponse(Long id, String category, String title, String content, String interest,
                            @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm") LocalDateTime expiredAt,
                            String imageIdentifier, String state, Long rewardCount, Long currentCount,
                            String groupName) {

    public QuestResponse(final Long id, final String category, final String title, final String content,
            final String interest, final LocalDateTime expiredAt, final String imageIdentifier,
            final String state, final Long rewardCount, final Long currentCount, final String groupName) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.interest = interest;
        this.expiredAt = expiredAt;
        this.imageIdentifier = imageIdentifier;
        this.state = state;
        this.rewardCount = rewardCount;
        this.currentCount = currentCount;
        this.groupName = groupName;
    }

    public static QuestResponse of(final QuestData questData) {
        return new QuestResponse(questData.getId(), questData.getCategory().toString(), questData.getTitle(),
                questData.getContent(), questData.getInterestName(), questData.getExpiredAt(),
                questData.getImageIdentifier(), questData.getState().toString(), questData.getRewardCount(),
                questData.getCurrentCount(), questData.getGroupName());
    }
}
