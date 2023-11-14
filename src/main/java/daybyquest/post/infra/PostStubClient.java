package daybyquest.post.infra;

import daybyquest.post.application.PostClient;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class PostStubClient implements PostClient {

    @Override
    public void requestJudge(final Long postId, final String label, final List<String> identifiers) {
    }
}
