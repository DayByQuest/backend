package daybyquest.dislike.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PostDislikeId.class)
public class PostDislike {

    @Id
    private Long userId;

    @Id
    private Long postId;

    public PostDislike(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
