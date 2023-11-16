package daybyquest.group.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface GroupRepository extends Repository<Group, Long> {

    Group save(final Group group);

    Optional<Group> findById(final Long id);

    boolean existsById(final Long id);

    boolean existsByName(final String name);
}