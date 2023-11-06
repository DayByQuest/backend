package daybyquest.profile.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveBadgeListRequest {

    private List<Long> badgeIds;
}
