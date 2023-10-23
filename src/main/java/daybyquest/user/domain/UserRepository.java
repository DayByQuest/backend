package daybyquest.user.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface UserRepository extends Repository<User, Long> {

    User save(final User user);

    Optional<User> findById(final Long id);

    Optional<User> findByUsername(final String username);

    boolean existsById(final Long id);

    boolean existsByUsername(final String username);

    boolean existsByEmail(final String email);

}