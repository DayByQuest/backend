package daybyquest.user.domain;

import java.io.InputStream;

public interface UserImages {

    void upload(final String identifier, final InputStream image);


    void remove(final String identifier);

    String getPublicUrl(final String identifier);
}
