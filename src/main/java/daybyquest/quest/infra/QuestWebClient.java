package daybyquest.quest.infra;

import daybyquest.global.error.exception.BadRequestException;
import daybyquest.quest.application.QuestClient;
import daybyquest.quest.dto.request.LabelQuestRequest;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("prod")
public class QuestWebClient implements QuestClient {

    private final WebClient webClient;

    public QuestWebClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void requestLabels(final Long questId, final List<String> identifiers) {
        final LabelQuestRequest request = new LabelQuestRequest(identifiers);
        webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("quest", questId.toString(), "shot").build())
                .bodyValue(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    throw new BadRequestException();
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> {
                    throw new BadRequestException();
                })
                .toBodilessEntity()
                .block();
    }
}
