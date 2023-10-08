package daybyquest.group.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupUserId implements Serializable {

    private Long group;

    private Long userId;

}
