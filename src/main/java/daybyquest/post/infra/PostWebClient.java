package daybyquest.post.infra;

import daybyquest.global.error.exception.BadRequestException;
import daybyquest.post.application.PostClient;
import daybyquest.post.dto.request.PostJudgeRequest;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Profile("prod")
public class PostWebClient implements PostClient {

    private final WebClient webClient;

    public PostWebClient(final WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void requestJudge(final Long postId, final String label, final List<String> identifiers) {
        final PostJudgeRequest request = new PostJudgeRequest(label, identifiers);
        webClient.post()
                .uri(uriBuilder -> uriBuilder.pathSegment("post", postId.toString(), "judge").build())
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
