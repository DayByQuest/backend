package daybyquest.user.dto.response;

import daybyquest.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserVisibilityResponse {

    private String visibility;

    private UserVisibilityResponse(final String visibility) {
        this.visibility = visibility;
    }

    public static UserVisibilityResponse of(final User user) {
        return new UserVisibilityResponse(user.getVisibility().toString());
    }
}
