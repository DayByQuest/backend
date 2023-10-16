package daybyquest.profile.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProfileSetting {

    @Id
    private Long userId;

    @Column
    @Convert(converter = ProfileBadgeListConverter.class)
    private List<Long> badgeList;

}
