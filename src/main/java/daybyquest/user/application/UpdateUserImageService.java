package daybyquest.user.application;

import daybyquest.global.error.exception.InvalidFileException;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserImages;
import daybyquest.user.domain.UserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
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
        final String oldIdentifier = user.getImageIdentifier();
        final String identifier = createIdentifier(file.getOriginalFilename());
        userImages.upload(identifier, getInputStream(file));
        user.updateImage(new Image(identifier));
        userImages.remove(oldIdentifier);
    }

    private InputStream getInputStream(final MultipartFile file) {
        try {
            return file.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new InvalidFileException();
        }
    }

    private String createIdentifier(final String originalName) {
        final String uuid = UUID.randomUUID().toString();
        return uuid + originalName;
    }
}
