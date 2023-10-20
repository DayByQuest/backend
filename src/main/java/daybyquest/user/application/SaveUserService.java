package daybyquest.user.application;

import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveUserService {

    private final UserRepository userRepository;

    private final UserValidator validator;

    private final String baseImageUrl;


    public SaveUserService(final UserRepository userRepository,
            final UserValidator validator, @Value("${profile.base-image-url}") final String baseImageUrl) {
        this.userRepository = userRepository;
        this.validator = validator;
        this.baseImageUrl = baseImageUrl;
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
                new Image(baseImageUrl));
    }
}
