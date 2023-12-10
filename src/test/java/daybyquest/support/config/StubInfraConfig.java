package daybyquest.support.config;

import daybyquest.image.domain.ImageIdentifierGenerator;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class StubInfraConfig {

    @Bean
    public StubImages images() {
        return new StubImages();
    }

    @Bean
    @Primary
    public ImageIdentifierGenerator imageIdentifierGenerator() {
        return new StubImageIdentifierGenerator();
    }
}
