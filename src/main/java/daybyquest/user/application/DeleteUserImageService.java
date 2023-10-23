package daybyquest.user.application;

import daybyquest.image.vo.BaseImageProperties;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserImages;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserImageService {

    private final Users users;

    private final UserImages userImages;

    private final BaseImageProperties baseImageProperties;

    public DeleteUserImageService(final Users users, final UserImages userImages,
            final BaseImageProperties baseImageProperties) {
        this.users = users;
        this.userImages = userImages;
        this.baseImageProperties = baseImageProperties;
    }

    @Transactional
    public void invoke(final Long loginId) {
        final User user = users.getById(loginId);
        userImages.remove(user.getImageIdentifier());
        user.updateImage(new Image(baseImageProperties.getUserIdentifier()));
    }
}

