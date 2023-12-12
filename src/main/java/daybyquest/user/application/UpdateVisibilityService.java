package daybyquest.user.application;

import daybyquest.user.domain.User;
import daybyquest.user.domain.UserVisibility;
import daybyquest.user.domain.Users;
import daybyquest.user.dto.request.UpdateUserVisibilityRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateVisibilityService {

    private final Users users;

    public UpdateVisibilityService(final Users users) {
        this.users = users;
    }

    @Transactional
    public void invoke(final Long loginId, final UpdateUserVisibilityRequest visibility) {
        final User user = users.getById(loginId);
        user.updateVisibility(UserVisibility.fromString(visibility.getVisibility()));
    }
}

