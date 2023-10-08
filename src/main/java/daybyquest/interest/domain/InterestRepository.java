package daybyquest.interest.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface InterestRepository extends Repository<Interest, String> {

    Interest save(Interest interest);

    Optional<Interest> findByName(String name);

}
