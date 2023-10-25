package daybyquest.user.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageProfilesResponse {

    private List<ProfileResponse> users;

    private Long lastId;

    public PageProfilesResponse(final List<ProfileResponse> users, final Long lastId) {
        this.users = users;
        this.lastId = lastId;
    }
}
