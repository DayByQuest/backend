package daybyquest.post.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeId implements Serializable {

    private Long post;

    private Long userId;

}
