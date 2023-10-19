package daybyquest.interest.domain;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface InterestRepository extends Repository<Interest, String> {

    Interest save(Interest interest);

    Optional<Interest> findByName(String name);

    List<Interest> findAllByNameIn(Collection<String> names);
}
