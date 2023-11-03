package daybyquest.badge.domain;

import daybyquest.user.domain.Users;
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
}
