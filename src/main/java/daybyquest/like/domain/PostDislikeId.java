package daybyquest.like.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDislikeId implements Serializable {

    private Long postId;

    private Long userId;

}
