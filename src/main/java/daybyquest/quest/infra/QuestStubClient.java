package daybyquest.quest.infra;

import daybyquest.quest.application.QuestClient;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("!prod")
public class QuestStubClient implements QuestClient {

    private final WebClient webClient;

    public QuestStubClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void requestLabels(final Long questId, final List<String> identifiers) {
        return;
    }
}
