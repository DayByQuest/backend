package daybyquest.group.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface GroupRepository extends Repository<Group, Long> {

    Group save(Group group);

    Optional<Group> findById(Long id);

}