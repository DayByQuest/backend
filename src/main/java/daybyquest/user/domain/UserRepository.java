package daybyquest.user.domain;

import daybyquest.global.error.exception.NotExistUserException;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);

    Optional<User> findById(Long id);

    boolean existsById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    default User getById(Long id) {
        return this.findById(id).orElseThrow(NotExistUserException::new);
    }
}