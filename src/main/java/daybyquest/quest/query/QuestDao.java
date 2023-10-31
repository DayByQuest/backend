package daybyquest.quest.query;

public interface QuestDao {

    QuestData getById(final Long userId, final Long id);
}
