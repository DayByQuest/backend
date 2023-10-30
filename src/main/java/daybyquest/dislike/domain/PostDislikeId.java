package daybyquest.dislike.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDislikeId implements Serializable {

    private Long userId;

    private Long postId;

}
