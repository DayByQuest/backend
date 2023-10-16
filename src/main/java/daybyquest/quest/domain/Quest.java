package daybyquest.quest.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.vo.Image;
import daybyquest.interest.domain.Interest;
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
import jakarta.persistence.ManyToOne;
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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long groupId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long badgeId;

    @ManyToOne(fetch = LAZY)
    private Interest interest;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private QuestCategory category;

    private Long rewardCount;

    private LocalDateTime expiredAt;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private QuestState state;

    @Column(nullable = false)
    private String imageDescription;

    @Column(nullable = false)
    private String label;

    @CreatedDate
    private LocalDateTime createdAt;

    @ElementCollection
    @CollectionTable(name = "quest_image", joinColumns = @JoinColumn(name = "quest_id"))
    private List<Image> images;

    private Quest(Long groupId, Long userId, Long badgeId, Interest interest, String title, String content,
        QuestCategory category, Long rewardCount, LocalDateTime expiredAt, QuestState state,
        String imageDescription, List<Image> images) {
        this.groupId = groupId;
        this.userId = userId;
        this.badgeId = badgeId;
        this.interest = interest;
        this.title = title;
        this.content = content;
        this.category = category;
        this.rewardCount = rewardCount;
        this.expiredAt = expiredAt;
        this.state = state;
        this.imageDescription = imageDescription;
        this.images = images;
    }

    public static Quest createNormalQuest(Long userId, Long badgeId, Interest interest, String title, String content,
        Long rewardCount, String imageDescription, List<Image> images) {
        return new Quest(null, userId, badgeId, interest, title, content, QuestCategory.NORMAL, rewardCount
            , null, QuestState.NEED_LABEL, imageDescription, images);
    }

    public static Quest createGroupQuest(Long groupId, Long userId, Interest interest, String title, String content,
        LocalDateTime expiredAt, String imageDescription, List<Image> images) {
        return new Quest(groupId, userId, null, interest, title, content, QuestCategory.GROUP, null
            , expiredAt, QuestState.NEED_LABEL, imageDescription, images);
    }

}
