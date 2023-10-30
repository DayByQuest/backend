package daybyquest.interest.domain;

import daybyquest.image.vo.Image;
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

    @Id
    @Column(length = 10)
    private String name;

    @Embedded
    private Image image;

    public Interest(final String name, final Image image) {
        this.name = name;
        this.image = image;
    }
}
