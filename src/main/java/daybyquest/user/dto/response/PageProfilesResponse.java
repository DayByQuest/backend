package daybyquest.user.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageProfilesResponse {

    private List<ProfileResponse> users;

    private Long nextId;

    public PageProfilesResponse(final List<ProfileResponse> users, final Long nextId) {
        this.users = users;
        this.nextId = nextId;
    }
}
