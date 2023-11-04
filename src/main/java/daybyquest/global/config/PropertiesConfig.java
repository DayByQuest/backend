package daybyquest.global.config;

import daybyquest.image.domain.BaseImageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(BaseImageProperties.class)
public class PropertiesConfig {

}
