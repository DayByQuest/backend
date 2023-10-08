package daybyquest.follow.domain;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public  class FollowId implements Serializable {
    private Long userId;
    private Long targetId;

}