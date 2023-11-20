package daybyquest.user.application;

import daybyquest.image.application.ImageService;
import daybyquest.image.domain.BaseImageProperties;
import daybyquest.image.domain.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpdateUserImageService {

    private static final String CATEGORY = "USER";

    private final Users users;

    private final ImageService imageService;

    private final BaseImageProperties properties;

    public UpdateUserImageService(final Users users, final ImageService imageService,
            final BaseImageProperties properties) {
        this.users = users;
        this.imageService = imageService;
        this.properties = properties;
    }

    @Transactional
    public void invoke(final Long loginId, final MultipartFile file) {
        final User user = users.getById(loginId);
        final String oldIdentifier = user.getImageIdentifier();
        final Image image = imageService.convertToImage(CATEGORY, file);
        user.updateImage(image);
        if (properties.isNotBase(oldIdentifier)) {
            imageService.remove(oldIdentifier);
        }
    }
}
