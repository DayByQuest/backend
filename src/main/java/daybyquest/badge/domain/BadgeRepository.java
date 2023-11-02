package daybyquest.badge.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface BadgeRepository extends Repository<Badge, Long> {

    Badge save(final Badge badge);

    Optional<Badge> findById(final Long id);

    boolean existsById(final Long id);
}
