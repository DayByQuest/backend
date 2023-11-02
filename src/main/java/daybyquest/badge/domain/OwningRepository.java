package daybyquest.badge.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface OwningRepository extends Repository<Owning, OwningId> {

    Owning save(final Owning owning);

    Optional<Owning> findByUserId(final Long userId);

}