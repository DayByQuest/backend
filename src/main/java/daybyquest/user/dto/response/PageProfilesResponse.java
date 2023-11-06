package daybyquest.user.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class PageProfilesResponse {

    private final List<ProfileResponse> users;

    private final Long lastId;

    public PageProfilesResponse(final List<ProfileResponse> users, final Long lastId) {
        this.users = users;
        this.lastId = lastId;
    }
}
