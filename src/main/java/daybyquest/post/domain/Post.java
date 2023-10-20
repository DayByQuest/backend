package daybyquest.post.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

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

    private boolean deleted;


    public Post(Long userId, Long questId, String content, List<Image> images, boolean deleted) {
        this.userId = userId;
        this.questId = questId;
        this.content = content;
        this.images = images;
        this.deleted = deleted;
        this.state = PostState.NOT_DECIDED;
    }
}
