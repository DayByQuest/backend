package daybyquest.user.application;

import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import daybyquest.user.dto.request.UpdateUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService {

    private final Users users;

    public UpdateUserService(final Users users) {
        this.users = users;
    }

    @Transactional
    public void invoke(final Long loginId, final UpdateUserRequest request) {
        final User user = users.getById(loginId);
        if (request.getUsername() != null) {
            users.validateUniqueUsername(request.getUsername());
            user.updateUsername(request.getUsername());
        }
        if (request.getName() != null) {
            user.updateName(request.getName());
        }
    }
}
