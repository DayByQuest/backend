package daybyquest.global.s3;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import daybyquest.global.error.exception.InvalidFileException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Images {

    private final AmazonS3 amazonS3;

    private final String bucket;

    public S3Images(final AmazonS3 amazonS3, @Value("${aws.bucket}") final String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    public void upload(final String category, final String identifier, final InputStream imageStream) {
        try {
            final String key = category + "/" + identifier;
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

    public void remove(final String category, final String identifier) {
        final String key = category + "/" + identifier;
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, key);
        amazonS3.deleteObject(deleteObjectRequest);
    }

    public String getPublicUrl(final String category, final String identifier, final Date expiration) {
        final String key = category + "/" + identifier;
        final GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request).toString();
    }
}
