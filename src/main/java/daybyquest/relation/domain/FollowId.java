package daybyquest.relation.domain;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class FollowId implements Serializable {

    private Long userId;
    private Long targetId;

}