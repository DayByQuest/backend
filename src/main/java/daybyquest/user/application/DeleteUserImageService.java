package daybyquest.user.application;

import daybyquest.image.application.ImageService;
import daybyquest.image.domain.BaseImageProperties;
import daybyquest.image.domain.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserImageService {

    private final Users users;

    private final ImageService imageService;

    private final BaseImageProperties properties;

    public DeleteUserImageService(final Users users, final ImageService imageService,
            final BaseImageProperties properties) {
        this.users = users;
        this.imageService = imageService;
        this.properties = properties;
    }

    @Transactional
    public void invoke(final Long loginId) {
        final User user = users.getById(loginId);
        if (properties.isNotBase(user.getImageIdentifier())) {
            imageService.remove(user.getImageIdentifier());
        }
        user.updateImage(new Image(properties.getUserIdentifier()));
    }
}

