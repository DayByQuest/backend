package daybyquest.image.infra;

import daybyquest.image.domain.ImageIdentifierGenerator;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDImageIdentifierGenerator implements ImageIdentifierGenerator {

    private static final String CATEGORY_DELIMITER = "/";

    private static final String UUID_DELIMITER = "-";

    @Override
    public String generate(final String category, final String originalName) {
        final String uuid = UUID.randomUUID().toString();
        return category + CATEGORY_DELIMITER + uuid + UUID_DELIMITER + originalName;
    }
}
