package daybyquest.relation.domain;

import static lombok.AccessLevel.PROTECTED;

import java.io.Serializable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
public  class BlockId implements Serializable {
    private Long userId;
    private Long targetId;

}