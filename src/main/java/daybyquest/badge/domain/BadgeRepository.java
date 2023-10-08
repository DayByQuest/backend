package daybyquest.badge.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface BadgeRepository extends Repository<Badge, Long> {

    Badge save(Badge badge);

    Optional<Badge> findById(Long id);

}
