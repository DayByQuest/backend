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
    @Convert(converter = ProfileBadgeIdsConverter.class)
    private List<Long> badgeIds;

    public ProfileSetting(final Long userId) {
        this.userId = userId;
        this.badgeIds = Collections.emptyList();
    }

    public void updateBadgeList(final List<Long> badgeIds) {
        if (badgeIds == null) {
            this.badgeIds = Collections.emptyList();
            return;
        }
        this.badgeIds = badgeIds;
        validateBadgeList();
    }

    private void validateBadgeList() {
        if (badgeIds.size() > MAX_BADGE_LIST_SIZE) {
            throw new InvalidDomainException(EXCEED_BADGE);
        }
    }
}
