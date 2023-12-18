package daybyquest.post.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_JUDGED_POST;
import static daybyquest.global.error.ExceptionCode.INVALID_POST_CONTENT;
import static daybyquest.global.error.ExceptionCode.INVALID_POST_IMAGE;
import static daybyquest.global.error.ExceptionCode.NOT_EXIST_USER;
import static daybyquest.global.error.ExceptionCode.NOT_LINKED_POST;
import static daybyquest.post.domain.PostState.FAIL;
import static daybyquest.post.domain.PostState.NEED_CHECK;
import static daybyquest.post.domain.PostState.NOT_DECIDED;
import static daybyquest.post.domain.PostState.SUCCESS;
import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
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
import jakarta.persistence.OrderColumn;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    private static final int MAX_IMAGE_SIZE = 5;

    private static final int MAX_CONTENT_SIZE = 500;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private Long questId;

    @CreatedDate
    private LocalDateTime uploadedAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 15)
    @Enumerated(EnumType.STRING)
    private PostState state;

    @Column(length = MAX_CONTENT_SIZE)
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_image", joinColumns = @JoinColumn(name = "post_id"))
    @OrderColumn(name = "order")
    private List<Image> images;

    public Post(Long userId, Long questId, String content, List<Image> images) {
        this.userId = userId;
        this.questId = questId;
        this.content = content;
        this.images = images;
        this.state = NOT_DECIDED;
        validateUserId();
        validateImages();
        validateContent();
        if (questId == null) {
            this.state = SUCCESS;
        }
    }

    private void validateUserId() {
        if (userId == null) {
            throw new InvalidDomainException(NOT_EXIST_USER);
        }
    }

    private void validateImages() {
        if (images.isEmpty() || images.size() > MAX_IMAGE_SIZE) {
            throw new InvalidDomainException(INVALID_POST_IMAGE);
        }
    }

    private void validateContent() {
        if (content.length() > MAX_CONTENT_SIZE) {
            throw new InvalidDomainException(INVALID_POST_CONTENT);
        }
    }

    public void success() {
        validateQuestLink();
        if (state != NOT_DECIDED && state != NEED_CHECK) {
            throw new InvalidDomainException(ALREADY_JUDGED_POST);
        }
        state = SUCCESS;
    }

    public void needCheck() {
        validateQuestLink();
        if (state != NOT_DECIDED) {
            throw new InvalidDomainException(ALREADY_JUDGED_POST);
        }
        state = NEED_CHECK;
    }

    public void fail() {
        validateQuestLink();
        if (state != NOT_DECIDED && state != NEED_CHECK) {
            throw new InvalidDomainException(ALREADY_JUDGED_POST);
        }
        state = FAIL;
    }

    public boolean isQuestLinked() {
        return questId != null;
    }

    private void validateQuestLink() {
        if (!isQuestLinked()) {
            throw new InvalidDomainException(NOT_LINKED_POST);
        }
    }

    public Long getQuestId() {
        validateQuestLink();
        return questId;
    }
}
