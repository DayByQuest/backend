package daybyquest.like.domain;

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
    private Long postId;

    @Id
    private Long userId;

    public PostDislike(Long postId, Long userId) {
        this.postId = postId;
        this.userId = userId;
    }

}
