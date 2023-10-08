package daybyquest.owner.domain;

import static jakarta.persistence.FetchType.LAZY;

import daybyquest.badge.domain.Badge;
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
@IdClass(OwnerId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Owner {

    @Id
    @ManyToOne(fetch = LAZY)
    private Badge badge;

    @Id
    private Long userId;

    @CreatedDate
    private LocalDateTime acquiredAt;

    public Owner(Badge badge, Long userId) {
        this.badge = badge;
        this.userId = userId;
    }
}
