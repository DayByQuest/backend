package daybyquest.relation.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface FollowRepository extends Repository<Follow, FollowId> {

    Follow save(final Follow follow);

    Optional<Follow> findByUserIdAndTargetId(final Long userId, final Long targetId);

    boolean existsByUserIdAndTargetId(final Long userId, final Long targetId);

    void delete(final Follow follow);
}