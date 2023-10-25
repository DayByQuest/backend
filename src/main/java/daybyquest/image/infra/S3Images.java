package daybyquest.image.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import daybyquest.global.error.exception.InvalidFileException;
import daybyquest.image.vo.Images;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class S3Images implements Images {

    private final AmazonS3 amazonS3;

    private final String bucket;

    public S3Images(final AmazonS3 amazonS3, @Value("${aws.bucket}") final String bucket) {
        this.amazonS3 = amazonS3;
        this.bucket = bucket;
    }

    @Override
    public void upload(final String identifier, final InputStream imageStream) {
        try {
            final ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(imageStream.available());
            final PutObjectRequest putObjectRequest = new PutObjectRequest(
                    bucket, identifier, imageStream, metadata
            );
            amazonS3.putObject(putObjectRequest);
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }

    @Override
    public void remove(final String identifier) {
        final DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, identifier);
        amazonS3.deleteObject(deleteObjectRequest);
    }
}
