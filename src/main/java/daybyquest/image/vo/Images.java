package daybyquest.image.vo;

import java.io.InputStream;

public interface Images {

    void upload(final String identifier, final InputStream imageStream);

    void remove(final String identifier);
}
