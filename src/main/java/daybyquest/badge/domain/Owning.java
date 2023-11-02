package daybyquest.badge.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@IdClass(OwningId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Owning {

    @Id
    private Long userId;

    @Id
    @ManyToOne(fetch = LAZY)
    private Badge badge;

    @CreatedDate
    private LocalDateTime acquiredAt;

    public Owning(final Long userId, final Badge badge) {
        this.userId = userId;
        this.badge = badge;
    }
}
