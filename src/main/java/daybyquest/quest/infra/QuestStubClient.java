package daybyquest.quest.infra;

import daybyquest.quest.application.QuestClient;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class QuestStubClient implements QuestClient {

    @Override
    public void requestLabels(final Long questId, final List<String> identifiers) {
    }
}
