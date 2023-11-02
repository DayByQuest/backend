package daybyquest.group.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.vo.Image;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Group {

    private static final int MAX_NAME_LENGTH = 15;

    private static final int MAX_DESCRIPTION_LENGTH = 200;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String interest;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(length = MAX_DESCRIPTION_LENGTH)
    private String description;

    private boolean deleted;

    @Embedded
    @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    private Image image;

    public Group(String interest, String name, String description, Image image) {
        this.interest = interest;
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.image = image;
        validate();
    }

    private void validate() {
        validateName();
        validateDescription();
    }

    private void validateName() {
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateDescription() {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new InvalidDomainException();
        }
    }
}
