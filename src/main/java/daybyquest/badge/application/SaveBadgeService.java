package daybyquest.badge.application;

import daybyquest.badge.domain.Badge;
import daybyquest.badge.domain.Badges;
import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveBadgeService {


    private static final String CATEGORY = "BADGE";

    private final Badges badges;

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SaveBadgeService(final Badges badges, final Images images,
            final ImageIdentifierGenerator generator) {
        this.badges = badges;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public void invoke(final String name, final MultipartFile file) {
        final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
        images.upload(identifier, MultipartFileUtils.getInputStream(file));
        final Badge badge = new Badge(name, new Image(identifier));
        badges.save(badge);
    }
}
