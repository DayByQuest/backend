package daybyquest.interest.domain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface InterestRepository extends Repository<Interest, String> {

    Interest save(final Interest interest);

    Optional<Interest> findByName(final String name);

    List<Interest> findAllByNameIn(final Collection<String> names);

    boolean existsByName(final String name);
}
