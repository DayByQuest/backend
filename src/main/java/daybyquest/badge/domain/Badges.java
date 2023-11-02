package daybyquest.badge.domain;

import org.springframework.stereotype.Component;

@Component
public class Badges {

    private final BadgeRepository badgeRepository;

    Badges(final BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    public void save(final Badge badge) {
        badgeRepository.save(badge);
    }
}
