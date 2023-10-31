package daybyquest.quest.query;

import daybyquest.image.vo.Image;
import daybyquest.participant.domain.ParticipantState;
import daybyquest.quest.domain.QuestCategory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class QuestData {

    private final Long id;

    private final String title;

    private final String content;

    private final String interestName;

    private final QuestCategory category;

    private ParticipantState state;

    private final LocalDateTime expiredAt;

    private Image image;

    private final Long rewardCount;

    private final Long currentCount;

    private String groupName;

    public QuestData(final Long id, final String title, final String content, final String interestName,
            final QuestCategory category, final LocalDateTime expiredAt,
            final Long rewardCount, final Long currentCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.interestName = interestName;
        this.category = category;
        this.expiredAt = expiredAt;
        this.rewardCount = rewardCount;
        this.currentCount = currentCount;
    }

    public void setState(final ParticipantState state) {
        this.state = state;
    }
}
