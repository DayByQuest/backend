package daybyquest.global.vo;

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
    private String imageUrl;

    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Image)) {
            return false;
        }
        final Image image = (Image) o;
        return this.imageUrl.equals(image.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(imageUrl);
    }

}
