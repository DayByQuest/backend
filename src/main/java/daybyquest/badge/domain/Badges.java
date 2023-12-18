package daybyquest.badge.domain;

import daybyquest.global.error.exception.NotExistBadgeException;
import org.springframework.stereotype.Component;

@Component
public class Badges {

    private final BadgeRepository badgeRepository;

    Badges(final BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public Badge save(final Badge badge) {
        return badgeRepository.save(badge);
    }

    public void validateExistentById(final Long id) {
        if (!badgeRepository.existsById(id)) {
            throw new NotExistBadgeException();
        }
    }

    public Badge getById(final Long id) {
        return badgeRepository.findById(id).orElseThrow(NotExistBadgeException::new);
    }
}
