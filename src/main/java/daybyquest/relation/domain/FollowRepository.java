package daybyquest.relation.domain;

import org.springframework.data.repository.Repository;

interface FollowRepository extends Repository<Follow, FollowId> {

    Follow save(final Follow follow);

    boolean existsByUserIdAndTargetId(final Long userId, final Long targetId);
}