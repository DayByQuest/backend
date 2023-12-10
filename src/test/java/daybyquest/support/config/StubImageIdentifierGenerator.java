package daybyquest.support.config;

import daybyquest.image.domain.ImageIdentifierGenerator;

public class StubImageIdentifierGenerator implements ImageIdentifierGenerator {

    @Override
    public String generate(final String category, final String originalName) {
        return originalName;
    }
}
