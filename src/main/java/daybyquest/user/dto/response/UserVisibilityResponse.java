package daybyquest.user.dto.response;

import daybyquest.user.domain.User;

public record UserVisibilityResponse(String visibility) {

    public static UserVisibilityResponse of(final User user) {
        return new UserVisibilityResponse(user.getVisibility().toString());
    }
}
