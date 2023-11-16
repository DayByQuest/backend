package daybyquest.quest.query;

import daybyquest.quest.domain.Quest;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface QuestRecommendDao extends Repository<Quest, Long> {

    @Query(value = "select q.id from quest q where q.category='NORMAL' and q.interest_name in :interests "
            + "and q.id >= FLOOR(1 + RAND() * (select MAX(quest.id) from quest)) "
            + "ORDER BY q.id limit :topN",
            nativeQuery = true)
    List<Long> getRecommendIds(final int topN, final Collection<String> interests);
}
