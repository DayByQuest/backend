package daybyquest.user.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.vo.BaseImageProperties;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpdateUserImageService {

    private static final String CATEGORY = "USER";

    private final Users users;

    private final Images images;

    private final ImageIdentifierGenerator generator;

    private final BaseImageProperties properties;

    public UpdateUserImageService(final Users users, final Images images,
            final ImageIdentifierGenerator generator, final BaseImageProperties properties) {
        this.users = users;
        this.images = images;
        this.generator = generator;
        this.properties = properties;
    }

    @Transactional
    public void invoke(final Long loginId, final MultipartFile file) {
        final User user = users.getById(loginId);
        final String oldIdentifier = user.getImageIdentifier();
        final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
        images.upload(identifier, MultipartFileUtils.getInputStream(file));
        user.updateImage(new Image(identifier));
        if (properties.isNotBase(oldIdentifier)) {
            images.remove(oldIdentifier);
        }
    }
}
