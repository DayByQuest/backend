package daybyquest.user.domain;

import java.io.InputStream;

public interface UserImages {

    void upload(final String identifier, final InputStream image);

    String getPublicUrl(final String indentifier);
}
