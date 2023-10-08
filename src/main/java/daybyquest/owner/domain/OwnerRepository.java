package daybyquest.owner.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface OwnerRepository extends Repository<Owner, OwnerId> {

    Owner save(Owner owner);

    Optional<Owner> findByUserId(Long userId);

}