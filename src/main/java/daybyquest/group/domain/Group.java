package daybyquest.group.domain;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import daybyquest.image.vo.Image;
import daybyquest.interest.domain.Interest;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Group {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Interest interest;

    @Column(nullable = false, length = 15)
    private String name;

    private String description;

    private boolean deleted;

    @Embedded
    @AttributeOverride(name = "imageUrl", column = @Column(name = "image_url"))
    private Image image;

    public Group(Interest interest, String name, String description, Image image) {
        this.interest = interest;
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.image = image;
    }
}
