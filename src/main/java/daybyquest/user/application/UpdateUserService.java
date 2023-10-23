package daybyquest.user.application;

import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.domain.UserValidator;
import daybyquest.user.dto.request.UpdateUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService {

    private final UserRepository userRepository;

    private final UserValidator validator;

    public UpdateUserService(final UserRepository userRepository, final UserValidator validator) {
        this.userRepository = userRepository;
        this.validator = validator;
    }

    @Transactional
    public void invoke(final Long loginId, final UpdateUserRequest request) {
        final User user = userRepository.getById(loginId);
        if (request.getUsername() != null) {
            validator.validateUniqueUsername(request.getUsername());
            user.updateUsername(request.getUsername());
        }
        if (request.getName() != null) {
            user.updateName(request.getName());
        }
    }
}
