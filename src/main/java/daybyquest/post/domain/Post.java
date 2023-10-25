package daybyquest.post.domain;

import static daybyquest.post.domain.PostState.NOT_DECIDED;
import static daybyquest.post.domain.PostState.PREPARING;
import static daybyquest.post.domain.PostState.SUCCESS;
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
import jakarta.persistence.OrderColumn;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

    private static final int MAX_IMAGE_SIZE = 5;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column
    private Long questId;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private PostState state;

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
        this.state = PREPARING;
        validateImages();
    }

    private void validateImages() {
        if (this.images.isEmpty() || this.images.size() > MAX_IMAGE_SIZE) {
            throw new InvalidDomainException();
        }
    }

    public void afterRequestDeciding() {
        if (this.state != PREPARING || this.images == null) {
            throw new InvalidDomainException();
        }
        this.state = NOT_DECIDED;
    }

    public void success() {
        if (this.state != PREPARING && this.state != NOT_DECIDED) {
            throw new InvalidDomainException();
        }
        this.state = SUCCESS;
    }
}
