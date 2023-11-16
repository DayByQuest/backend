package daybyquest.quest.query;

import java.util.Collection;
import java.util.List;

public interface QuestDao {

    QuestData getById(final Long userId, final Long id);

    List<QuestData> findAllByGroupId(final Long userId, final Long groupId);

    List<QuestData> findAllByIdIn(final Long userId, final Collection<Long> ids);
}
