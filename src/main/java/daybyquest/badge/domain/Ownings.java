package daybyquest.badge.domain;

import static daybyquest.global.error.ExceptionCode.NOT_OWNING_BADGE;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.user.domain.Users;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Ownings {

    private final OwningRepository owningRepository;

    private final Badges badges;

    private final Users users;

    Ownings(final OwningRepository owningRepository, final Badges badges, final Users users) {
        this.owningRepository = owningRepository;
        this.badges = badges;
        this.users = users;
    }

    public void saveByUserIdAndBadgeId(final Long userId, final Long badgeId) {
        users.validateExistentById(userId);
        save(new Owning(userId, badges.getById(badgeId)));
    }

    private void save(final Owning owning) {
        owningRepository.save(owning);
    }

    public void validateOwningByBadgeIds(final Long userId, final List<Long> badgeIds) {
        final List<Owning> ownings = owningRepository.findAllByUserIdAndBadgeIdIn(userId, badgeIds);
        if (ownings.size() != badgeIds.size()) {
            throw new InvalidDomainException(NOT_OWNING_BADGE);
        }
        ownings.forEach(
                owning -> validateContainOwning(badgeIds, owning)
        );
    }

    private void validateContainOwning(final Collection<Long> badgeIds, final Owning owning) {
        if (!badgeIds.contains(owning.getBadgeId())) {
            throw new InvalidDomainException(NOT_OWNING_BADGE);
        }
    }
}
