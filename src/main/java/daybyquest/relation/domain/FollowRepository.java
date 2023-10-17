package daybyquest.relation.domain;

import org.springframework.data.repository.Repository;

public interface FollowRepository extends Repository<Follow, FollowId> {

    Follow save(Follow follow);

}