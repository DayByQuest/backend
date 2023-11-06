package daybyquest.profile.domain;

import static daybyquest.global.error.ExceptionCode.EXCEED_BADGE;
import static lombok.AccessLevel.PROTECTED;

import daybyquest.global.error.exception.InvalidDomainException;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class ProfileSetting {

    private static final int MAX_BADGE_LIST_SIZE = 10;

    @Id
    private Long userId;

    @Column
    @Convert(converter = ProfileBadgeListConverter.class)
    private List<Long> badgeList;

    public ProfileSetting(final Long userId) {
        this.userId = userId;
        this.badgeList = Collections.emptyList();
    }

    public void updateBadgeList(final List<Long> badgeList) {
        if (badgeList == null) {
            this.badgeList = Collections.emptyList();
            return;
        }
        this.badgeList = badgeList;
        validateBadgeList();
    }

    private void validateBadgeList() {
        if (badgeList.size() > MAX_BADGE_LIST_SIZE) {
            throw new InvalidDomainException(EXCEED_BADGE);
        }
    }
}
