package daybyquest.image.domain;

import static lombok.AccessLevel.PROTECTED;

import daybyquest.global.error.exception.InvalidFileException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class Image {

    private static final int MAX_IDENTIFIER_LENGTH = 255;

    @Column(nullable = false)
    private String identifier;

    public Image(String identifier) {
        this.identifier = identifier;
        validateIdentifier();
    }

    private void validateIdentifier() {
        if (identifier == null || identifier.isEmpty() || identifier.length() > MAX_IDENTIFIER_LENGTH) {
            throw new InvalidFileException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Image image)) {
            return false;
        }
        return this.identifier.equals(image.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(identifier);
    }
}
