package daybyquest.profile.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveBadgeListRequest {

    @NotNull
    private List<Long> badgeIds;
}
