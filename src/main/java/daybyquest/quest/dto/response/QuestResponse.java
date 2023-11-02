package daybyquest.quest.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import daybyquest.quest.query.QuestData;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestResponse {

    private Long id;

    private String title;

    private String content;

    private String interest;

    private String category;

    private String state;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expiredAt;

    private String imageIdentifier;

    private Long rewardCount;

    private Long currentCount;

    private String groupName;

    private QuestResponse(final Long id, final String category, final String title, final String content,
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
