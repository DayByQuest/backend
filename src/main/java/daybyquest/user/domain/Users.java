package daybyquest.user.domain;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_EMAIL;
import static daybyquest.global.error.ExceptionCode.DUPLICATED_USERNAME;

import daybyquest.global.error.exception.BadRequestException;
import daybyquest.global.error.exception.NotExistUserException;
import org.springframework.stereotype.Component;

@Component
public class Users {

    private final UserRepository userRepository;

    public Users(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

    public User getById(final Long id) {
        return userRepository.findById(id).orElseThrow(NotExistUserException::new);
    }

    public User getByUsername(final String username) {
        return userRepository.findByUsername(username).orElseThrow(NotExistUserException::new);
    }

    void validateExistentById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotExistUserException();
        }
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
