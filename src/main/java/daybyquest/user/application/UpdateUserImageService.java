package daybyquest.user.application;

import daybyquest.global.error.exception.InvalidFileException;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserImages;
import daybyquest.user.domain.UserRepository;
import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UpdateUserImageService {

    private final UserRepository userRepository;

    private final UserImages userImages;

    public UpdateUserImageService(final UserRepository userRepository,
            final UserImages userImages) {
        this.userRepository = userRepository;
        this.userImages = userImages;
    }

    @Transactional
    public void invoke(final Long loginId, final MultipartFile file) {
        final User user = userRepository.getById(loginId);
        final String uuid = UUID.randomUUID().toString();
        final String identifier = uuid + file.getOriginalFilename();
        try {
            userImages.upload(identifier, file.getInputStream());
            user.updateImage(new Image(identifier));
        } catch (IOException e) {
            throw new InvalidFileException();
        }
    }
}
