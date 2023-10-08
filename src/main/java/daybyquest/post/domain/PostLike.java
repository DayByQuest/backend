package daybyquest.post.domain;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PostLikeId.class)
public class PostLike {

    @Id
    @ManyToOne(fetch = LAZY)
    private Post post;

    @Id
    private Long userId;

    public PostLike(Post post, Long userId) {
        this.post = post;
        this.userId = userId;
    }

}
