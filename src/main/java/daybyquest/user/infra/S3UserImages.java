package daybyquest.user.infra;

import daybyquest.global.s3.S3Images;
import daybyquest.image.vo.BaseImageProperties;
import daybyquest.user.domain.UserImages;
import java.io.InputStream;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class S3UserImages implements UserImages {

    private static final String CATEGORY_NAME = "USER";

    private static final long PUBLIC_URL_EXPIRATION = 1000 * 60 * 60;

    private final S3Images s3Images;

    private final BaseImageProperties baseImageProperties;

    public S3UserImages(final S3Images s3Images, final BaseImageProperties baseImageProperties) {
        this.s3Images = s3Images;
        this.baseImageProperties = baseImageProperties;
    }

    @Override
    public void upload(final String identifier, final InputStream imageStream) {
        s3Images.upload(CATEGORY_NAME, identifier, imageStream);
    }

    @Override
    public void remove(final String identifier) {
        if (identifier.equals(baseImageProperties.getUserIdentifier())) {
            return;
        }
        s3Images.remove(CATEGORY_NAME, identifier);
    }

    @Override
    public String getPublicUrl(final String identifier) {
        final long expirationLong = System.currentTimeMillis() + PUBLIC_URL_EXPIRATION;
        final Date expiration = new Date(expirationLong);
        return s3Images.getPublicUrl(CATEGORY_NAME, identifier, expiration);
    }
}
