package daybyquest.badge.application;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.domain.Badges;
import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveBadgeService {

    private static final String CATEGORY = "BADGE";

    private final Badges badges;

    private final ImageService imageService;

    public SaveBadgeService(final Badges badges, final ImageService imageService) {
        this.badges = badges;
        this.imageService = imageService;
    }

    @Transactional
    public void invoke(final String name, final MultipartFile file) {
        final Image image = imageService.convertToImage(CATEGORY, file);
        final Badge badge = new Badge(name, image);
        badges.save(badge);
    }
}
