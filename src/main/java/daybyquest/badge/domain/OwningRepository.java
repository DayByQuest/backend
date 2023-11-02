package daybyquest.badge.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface OwningRepository extends Repository<Owning, OwningId> {

    Owning save(Owning owning);

    Optional<Owning> findByUserId(Long userId);

}