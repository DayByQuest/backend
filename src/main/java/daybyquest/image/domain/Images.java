package daybyquest.image.domain;

import java.io.InputStream;

public interface Images {

    Image upload(final String identifier, final InputStream imageStream);

    void remove(final String identifier);
}
