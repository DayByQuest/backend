package daybyquest.badge.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.vo.Image;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Embedded
    private Image image;

    public Badge(String name, Image image) {
        this.name = name;
        this.image = image;
    }
}
