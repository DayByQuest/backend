package daybyquest.user.application;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_EMAIL;
import static daybyquest.global.error.ExceptionCode.DUPLICATED_USERNAME;

import daybyquest.global.error.exception.BadRequestException;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class UserValidator {

    private final UserRepository userRepository;

    public UserValidator(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateUniqueness(final User user) {
        validateUniqueUsername(user.getUsername());
        validateUniqueEmail(user.getEmail());
    }

    public void validateUniqueUsername(final String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException(DUPLICATED_USERNAME);
        }
    }

    public void validateUniqueEmail(final String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(DUPLICATED_EMAIL);
        }
    }
}
