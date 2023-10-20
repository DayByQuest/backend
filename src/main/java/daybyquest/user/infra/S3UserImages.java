package daybyquest.user.infra;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import daybyquest.global.error.exception.InvalidFileException;
import daybyquest.image.vo.BaseImageProperties;
import daybyquest.user.domain.UserImages;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3UserImages implements UserImages {

    private static final String FOLDER_NAME = "USER";

    private static final long PUBLIC_URL_EXPIRATION = 1000 * 60 * 2;

    private final AmazonS3 amazonS3;

    private final String bucket;

    private final BaseImageProperties baseImageProperties;

    public S3UserImages(final AmazonS3 amazonS3, @Value("${aws.bucket}") final String bucket,
            final BaseImageProperties baseImageProperties) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
        this.baseImageProperties = baseImageProperties;
    }

    @Override
    public void upload(final String identifier, final InputStream imageStream) {
        try {
            final String key = FOLDER_NAME + "/" + identifier;
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageStream.available());
            final PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket, key, imageStream, metadata
            );
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }

    @Override
    public void remove(final String identifier) {
        if (identifier.equals(baseImageProperties.getUserIdentifier())) {
            return;
        }
        final String key = FOLDER_NAME + "/" + identifier;
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    @Override
    public String getPublicUrl(final String identifier) {
        final long expirationLong = System.currentTimeMillis() + PUBLIC_URL_EXPIRATION;
        final Date expiration = new Date(expirationLong);
        final String key = FOLDER_NAME + "/" + identifier;
        final GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request).toString();
    }
}
