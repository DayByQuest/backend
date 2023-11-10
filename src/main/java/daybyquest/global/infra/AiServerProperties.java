package daybyquest.global.infra;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "ai")
public class AiServerProperties {

    private final String domain;

    @ConstructorBinding
    public AiServerProperties(final String domain) {
        this.domain = domain;
    }
}
