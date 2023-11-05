package daybyquest.interest.domain;

import static daybyquest.global.error.ExceptionCode.INVALID_INTEREST_NAME;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    private static final int MAX_NAME_LENGTH = 20;

    @Id
    @Column(length = MAX_NAME_LENGTH)
    private String name;

    @Embedded
    private Image image;

    public Interest(final String name, final Image image) {
        this.name = name;
        this.image = image;
        validateName();
    }

    public String getImageIdentifier() {
        return image.getIdentifier();
    }

    private void validateName() {
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidDomainException(INVALID_INTEREST_NAME);
        }
    }
}
