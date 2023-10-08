package daybyquest.post.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.vo.Image;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String content;

    @ElementCollection
    @CollectionTable(name = "post_image", joinColumns = @JoinColumn(name = "post_id"))
    @OrderColumn(name = "order")
    private List<Image> images;

    private boolean deleted;

    @Enumerated
    private PostLink link;

    public Post(Long userId, Long questId, String content, List<Image> images, boolean deleted) {
        this.userId = userId;
        this.content = content;
        this.images = images;
        this.deleted = deleted;
        this.link = new PostLink(questId);
    }
}
