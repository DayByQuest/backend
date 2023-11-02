package daybyquest.quest.query;

import daybyquest.badge.domain.Badge;
import daybyquest.group.domain.Group;
import daybyquest.image.vo.Image;
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

    private Image image;

    private final Long rewardCount;

    private final Long currentCount;

    private String groupName;

    public QuestData(final Long id, final Long groupId, final Long badgeId, final String title,
            final String content, final String interestName, final QuestCategory category,
            final LocalDateTime expiredAt, final Long rewardCount, final Long currentCount) {
        this.id = id;
        this.groupId = groupId;
        this.badgeId = badgeId;
        this.title = title;
        this.content = content;
        this.interestName = interestName;
        this.category = category;
        this.expiredAt = expiredAt;
        this.rewardCount = rewardCount;
        this.currentCount = currentCount;
    }

    public String getImageIdentifier() {
        return image.getImageIdentifier();
    }

    public void setState(final ParticipantState state) {
        this.state = state;
    }

    public boolean isGroupQuest() {
        return category == QuestCategory.GROUP;
    }

    public void setNormalDetail(final Badge badge) {
        this.image = badge.getImage();
    }

    public void setGroupDetail(final Group group) {
        this.image = group.getImage();
        this.groupName = group.getName();
    }
}
