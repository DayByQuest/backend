package daybyquest.image.domain;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ImageIdentifierGenerator {

    public String generateIdentifier(final String category, final String originalName) {
        final String uuid = UUID.randomUUID().toString();
        return category + "/" + uuid + originalName;
    }
}
