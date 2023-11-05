package daybyquest.comment.domain;

import static daybyquest.global.error.ExceptionCode.INVALID_COMMENT_CONTENT;
import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    private static final int MAX_CONTENT_SIZE = 200;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false, length = MAX_CONTENT_SIZE)
    private String content;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public Comment(Long userId, Long postId, String content) {
        this.userId = userId;
        this.postId = postId;
        this.content = content;
        validateContent();
    }

    private void validateContent() {
        if (content.isEmpty() || content.length() > MAX_CONTENT_SIZE) {
            throw new InvalidDomainException(INVALID_COMMENT_CONTENT);
        }
    }
}
