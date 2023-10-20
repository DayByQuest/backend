package daybyquest.user.application;

import daybyquest.image.vo.BaseImageProperties;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveUserService {

    private final UserRepository userRepository;

    private final UserValidator validator;

    private final BaseImageProperties baseImageProperties;


    public SaveUserService(final UserRepository userRepository,
            final UserValidator validator, final BaseImageProperties baseImageProperties) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.baseImageProperties = baseImageProperties;
    }

    @Transactional
    public Long invoke(final SaveUserRequest request) {
        final User user = toEntity(request);
        validator.validateUniqueness(user);
        final User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private User toEntity(final SaveUserRequest request) {
        return new User(request.getUsername(), request.getEmail(), request.getName(),
                new Image(baseImageProperties.getUserIdentifier()));
    }
}
