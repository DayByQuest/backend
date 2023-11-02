package daybyquest.badge.domain;

import org.springframework.stereotype.Component;

@Component
public class Ownings {

    private final OwningRepository owningRepository;

    private final Badges badges;

    Ownings(final OwningRepository owningRepository, final Badges badges) {
        this.owningRepository = owningRepository;
        this.badges = badges;
    }

    public void save(final Owning owning) {
        owningRepository.save(owning);
    }

    public void saveByUserIdAndBadgeId(final Long userId, final Long badgeId) {
        save(new Owning(userId, badges.getById(badgeId)));
    }
}
