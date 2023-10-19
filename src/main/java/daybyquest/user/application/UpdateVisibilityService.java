package daybyquest.user.application;

import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.domain.UserVisibility;
import daybyquest.user.dto.request.UpdateUserVisibilityRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateVisibilityService {

    private final UserRepository userRepository;

    public UpdateVisibilityService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void invoke(final Long loginId, final UpdateUserVisibilityRequest visibility) {
        final User user = userRepository.getById(loginId);
        user.updateVisibility(UserVisibility.valueOf(visibility.getVisibility()));
    }
}

