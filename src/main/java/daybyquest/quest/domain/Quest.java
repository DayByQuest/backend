package daybyquest.quest.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_LABELED;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_CONTENT;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_EXPIRED_AT;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_IMAGES;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_IMAGE_DESCRIPTION;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_NAME;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_REWARD;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_REWARD_COUNT;
import static daybyquest.global.error.ExceptionCode.NOT_EXIST_GROUP;
import static daybyquest.quest.domain.QuestState.ACTIVE;
import static daybyquest.quest.domain.QuestState.NEED_LABEL;
import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
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

    private static final int MAX_TITLE_LENGTH = 30;

    private static final int MAX_CONTENT_LENGTH = 300;

    private static final int MAX_IMAGE_DESCRIPTION_LENGTH = 100;

    private static final Long MIN_REWARD_COUNT = 1L;

    private static final Long MAX_REWARD_COUNT = 365L;

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

    @Embedded
    private Image image;

    private Quest(final Long groupId, final Long badgeId, final String interestName, final String title,
            final String content, final QuestCategory category, final Long rewardCount,
            final LocalDateTime expiredAt, final String imageDescription, final List<Image> images,
            final Image image) {
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
        this.image = image;
        validate();
    }

    public static Quest createNormalQuest(final Long badgeId, final String interestName, final String title,
            final String content, final Long rewardCount, final String imageDescription,
            final List<Image> images, final Image image) {
        return new Quest(null, badgeId, interestName, title, content, QuestCategory.NORMAL, rewardCount
                , null, imageDescription, images, image);
    }

    public static Quest createGroupQuest(final Long groupId, final String interestName, final String title,
            final String content, final LocalDateTime expiredAt, final String imageDescription,
            final List<Image> images, final Image image) {
        validateGroupId(groupId);
        return new Quest(groupId, null, interestName, title, content, QuestCategory.GROUP, null
                , expiredAt, imageDescription, images, image);
    }

    private static void validateGroupId(final Long groupId) {
        if (groupId == null) {
            throw new InvalidDomainException(NOT_EXIST_GROUP);
        }
    }

    private void validate() {
        validateTitle();
        validateContent();
        validateImageDescription();
        validateRewardCount();
        validateImageCount();
        validateReward();
        validateExpiredAt();
    }

    private void validateTitle() {
        if (title.isEmpty() || title.length() > MAX_TITLE_LENGTH) {
            throw new InvalidDomainException(INVALID_QUEST_NAME);
        }
    }

    private void validateContent() {
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new InvalidDomainException(INVALID_QUEST_CONTENT);
        }
    }

    private void validateImageDescription() {
        if (imageDescription.isEmpty() || imageDescription.length() > MAX_IMAGE_DESCRIPTION_LENGTH) {
            throw new InvalidDomainException(INVALID_QUEST_IMAGE_DESCRIPTION);
        }
    }

    private void validateRewardCount() {
        if (rewardCount == null) {
            return;
        }
        if (rewardCount < MIN_REWARD_COUNT || rewardCount > MAX_REWARD_COUNT) {
            throw new InvalidDomainException(INVALID_QUEST_REWARD_COUNT);
        }
    }

    private void validateImageCount() {
        if (images.size() != IMAGE_COUNT) {
            throw new InvalidDomainException(INVALID_QUEST_IMAGES);
        }
    }

    private void validateReward() {
        if (rewardCount == null ^ badgeId == null) {
            throw new InvalidDomainException(INVALID_QUEST_REWARD);
        }
    }

    private void validateExpiredAt() {
        if (expiredAt != null && expiredAt.isBefore(LocalDateTime.now())) {
            throw new InvalidDomainException(INVALID_QUEST_EXPIRED_AT);
        }
    }

    public void setLabel(final String label) {
        if (this.state != NEED_LABEL) {
            throw new InvalidDomainException(ALREADY_LABELED);
        }
        this.label = label;
        this.state = ACTIVE;
    }
}
