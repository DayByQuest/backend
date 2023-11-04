package daybyquest.image.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Image {

    @Column(nullable = false)
    private String imageIdentifier;

    public Image(String imageIdentifier) {
        this.imageIdentifier = imageIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Image image)) {
            return false;
        }
        return this.imageIdentifier.equals(image.imageIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(imageIdentifier);
    }

}
