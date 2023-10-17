package daybyquest.relation.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@IdClass(FollowId.class)
public class Follow {

    @Id
    private Long userId;

    @Id
    private Long targetId;

    public Follow(Long userId, Long targetId) {
        this.userId = userId;
        this.targetId = targetId;
    }
}
