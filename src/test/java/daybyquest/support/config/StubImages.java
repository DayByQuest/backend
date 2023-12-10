package daybyquest.support.config;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Images;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class StubImages implements Images {

    private final Set<String> identifiers;

    public StubImages() {
        this.identifiers = new HashSet<>();
    }

    @Override
    public void upload(final String identifier, final InputStream imageStream) {
        identifiers.add(identifier);
    }

    @Override
    public void remove(final String identifier) {
        if (!identifiers.contains(identifier)) {
            throw new InvalidDomainException();
        }
        identifiers.remove(identifier);
    }

    public boolean hasUploadImage(final String identifier) {
        return identifiers.contains(identifier);
    }
}
