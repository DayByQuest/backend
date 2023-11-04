package daybyquest.image.domain;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "image.base")
public class BaseImageProperties {

    private final String userIdentifier;

    @ConstructorBinding
    public BaseImageProperties(final String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public boolean isNotBase(final String identifier) {
        return !userIdentifier.equals(identifier);
    }

    public Image getImage() {
        return new Image(userIdentifier);
    }
}
