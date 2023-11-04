package daybyquest.user.application;

import daybyquest.image.domain.BaseImageProperties;
import daybyquest.image.domain.Image;
import daybyquest.image.domain.Images;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserImageService {

    private final Users users;

    private final Images images;

    private final BaseImageProperties properties;

    public DeleteUserImageService(final Users users, final Images images,
            final BaseImageProperties properties) {
        this.users = users;
        this.images = images;
        this.properties = properties;
    }

    @Transactional
    public void invoke(final Long loginId) {
        final User user = users.getById(loginId);
        if (properties.isNotBase(user.getImageIdentifier())) {
            images.remove(user.getImageIdentifier());
        }
        user.updateImage(new Image(properties.getUserIdentifier()));
    }
}

