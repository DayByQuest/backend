package daybyquest.user.application;

import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import daybyquest.user.dto.response.UserVisibilityResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetVisibilityService {

    private final Users users;

    public GetVisibilityService(final Users users) {
        this.users = users;
    }

    @Transactional(readOnly = true)
    public UserVisibilityResponse invoke(final Long loginId) {
        final User user = users.getById(loginId);
        return UserVisibilityResponse.of(user);
    }
}
