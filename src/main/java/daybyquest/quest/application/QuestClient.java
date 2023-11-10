package daybyquest.quest.application;

import java.util.List;

public interface QuestClient {

    void requestLabels(final Long questId, final List<String> identifiers);
}
