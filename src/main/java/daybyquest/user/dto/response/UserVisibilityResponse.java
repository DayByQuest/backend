package daybyquest.user.dto.response;

import daybyquest.user.domain.User;
import lombok.Getter;

@Getter
public class UserVisibilityResponse {

    private final String visibility;

    private UserVisibilityResponse(final String visibility) {
        this.visibility = visibility;
    }

    public static UserVisibilityResponse of(final User user) {
        return new UserVisibilityResponse(user.getVisibility().toString());
    }
}
