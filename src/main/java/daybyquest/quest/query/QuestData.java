package daybyquest.quest.query;

import daybyquest.image.domain.Image;
import daybyquest.participant.domain.ParticipantState;
import daybyquest.quest.domain.QuestCategory;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class QuestData {

    private final Long id;

    private final Long groupId;

    private final Long badgeId;

    private final String title;

    private final String content;

    private final String interestName;

    private final QuestCategory category;

    private ParticipantState state;

    private final LocalDateTime expiredAt;

    private final Image image;

    private final Long rewardCount;

    private final Long currentCount;

    private final String groupName;

    public QuestData(final Long id, final Long groupId, final Long badgeId, final String title,
            final String content, final String interestName, final QuestCategory category,
            final ParticipantState state, final LocalDateTime expiredAt, final Image image,
            final Long rewardCount, final Long currentCount, final String groupName) {
        this.id = id;
        this.groupId = groupId;
        this.badgeId = badgeId;
        this.title = title;
        this.content = content;
        this.interestName = interestName;
        this.category = category;
        this.expiredAt = expiredAt;
        this.image = image;
        this.rewardCount = rewardCount;
        this.currentCount = currentCount;
        this.groupName = groupName;
        if (state == null) {
            this.state = ParticipantState.NOT;
            return;
        }
        this.state = state;
    }

    public String getImageIdentifier() {
        return image.getIdentifier();
    }
}
