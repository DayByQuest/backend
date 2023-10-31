package daybyquest.quest.domain;

import static daybyquest.quest.domain.QuestState.ACTIVE;
import static daybyquest.quest.domain.QuestState.NEED_LABEL;
import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.vo.Image;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Quest {

    private static final int MAX_TITLE_LENGTH = 15;

    private static final int MAX_CONTENT_LENGTH = 300;

    private static final int MAX_IMAGE_DESCRIPTION_LENGTH = 300;

    private static final int MAX_REWARD_COUNT = 100;

    private static final int IMAGE_COUNT = 3;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long groupId;

    private Long badgeId;

    private String interestName;

    @Column(nullable = false, length = MAX_TITLE_LENGTH)
    private String title;

    @Column(length = MAX_CONTENT_LENGTH)
    private String content;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private QuestCategory category;

    private Long rewardCount;

    private LocalDateTime expiredAt;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private QuestState state;

    @Column(nullable = false, length = MAX_IMAGE_DESCRIPTION_LENGTH)
    private String imageDescription;

    @Column(length = 15)
    private String label;

    @CreatedDate
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "quest_image", joinColumns = @JoinColumn(name = "quest_id"))
    private List<Image> images;

    private Quest(Long groupId, Long badgeId, String interestName, String title, String content,
            QuestCategory category, Long rewardCount, LocalDateTime expiredAt,
            String imageDescription, List<Image> images) {
        this.groupId = groupId;
        this.badgeId = badgeId;
        this.interestName = interestName;
        this.title = title;
        this.content = content;
        this.category = category;
        this.rewardCount = rewardCount;
        this.expiredAt = expiredAt;
        this.state = NEED_LABEL;
        this.imageDescription = imageDescription;
        this.images = images;
        validate();
    }

    public static Quest createNormalQuest(Long badgeId, String interestName, String title,
            String content,
            Long rewardCount, String imageDescription, List<Image> images) {
        return new Quest(null, badgeId, interestName, title, content, QuestCategory.NORMAL, rewardCount
                , null, imageDescription, images);
    }

    public static Quest createGroupQuest(Long groupId, String interestName, String title,
            String content,
            LocalDateTime expiredAt, String imageDescription, List<Image> images) {
        return new Quest(groupId, null, interestName, title, content, QuestCategory.GROUP, null
                , expiredAt, imageDescription, images);
    }

    private void validate() {
        validateTitle();
        validateContent();
        validateImageDescription();
        validateRewardCount();
        validateImageCount();
    }

    private void validateTitle() {
        if (title.isEmpty() || title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateContent() {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateImageDescription() {
        if (imageDescription.isEmpty() || imageDescription.length() > MAX_IMAGE_DESCRIPTION_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateRewardCount() {
        if (rewardCount > MAX_REWARD_COUNT) {
            throw new InvalidDomainException();
        }
    }

    private void validateImageCount() {
        if (images.size() != IMAGE_COUNT) {
            throw new InvalidDomainException();
        }
    }

    public void setLabel(final String label) {
        if (this.state != NEED_LABEL) {
            throw new InvalidDomainException();
        }
        this.label = label;
        this.state = ACTIVE;
    }
}
