package daybyquest.badge.domain;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

interface OwningRepository extends Repository<Owning, OwningId> {

    Owning save(final Owning owning);

    @Query("SELECT o  FROM Owning o WHERE o.userId=:userId and o.badge.id IN :badgeIds")
    List<Owning> findAllByUserIdAndBadgeIdIn(final Long userId, final Collection<Long> badgeIds);
}