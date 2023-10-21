package daybyquest.user.application;

import daybyquest.image.vo.BaseImageProperties;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserImages;
import daybyquest.user.domain.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserImageService {

    private final UserRepository userRepository;

    private final UserImages userImages;

    private final BaseImageProperties baseImageProperties;

    public DeleteUserImageService(final UserRepository userRepository, final UserImages userImages,
            final BaseImageProperties baseImageProperties) {
        this.userRepository = userRepository;
        this.userImages = userImages;
        this.baseImageProperties = baseImageProperties;
    }

    @Transactional
    public void invoke(final Long loginId) {
        final User user = userRepository.getById(loginId);
        userImages.remove(user.getImageIdentifier());
        user.updateImage(new Image(baseImageProperties.getUserIdentifier()));
    }
}

