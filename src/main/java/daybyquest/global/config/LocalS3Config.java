package daybyquest.global.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class LocalS3Config {

    private final String accessKey;

    private final String secretKey;

    private final String region;

    private final String bucket;

    private final String endpoint;

    public LocalS3Config(@Value("${aws.credentials.access-key}") final String accessKey,
            @Value("${aws.credentials.secret-key}") final String secretKey,
            @Value("${aws.region}") final String region,
            @Value("${aws.bucket}") final String bucket, @Value("${aws.endpoint}") final String endpoint) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.region = region;
        this.bucket = bucket;
        this.endpoint = endpoint;
    }

    @Bean
    public AmazonS3 amazonS3() {
        final EndpointConfiguration endpoint = new EndpointConfiguration(this.endpoint, region);
        final AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(endpoint)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
        amazonS3.createBucket(bucket);
        return amazonS3;
    }
}
