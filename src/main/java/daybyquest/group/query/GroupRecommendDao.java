package daybyquest.group.query;

import daybyquest.group.domain.Group;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface GroupRecommendDao extends Repository<Group, Long> {

    @Query(value = "select g.id from `group` g where g.interest in :interests "
            + "and g.id >= FLOOR(1 + RAND() * (select MAX(`group`.id) from `group`)) "
            + "ORDER BY g.id limit :topN",
            nativeQuery = true)
    List<Long> getRecommendIds(final int topN, final Collection<String> interests);
}
