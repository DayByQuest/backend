package daybyquest.user.domain;

import java.io.InputStream;

public interface UserImages {

    String upload(final String identifier, final InputStream image);

    String getPublicUrl(final String privateUrl);
}
