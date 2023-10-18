package daybyquest.user.application;

import daybyquest.global.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SaveUserService {

    private final UserRepository userRepository;

    private final String baseImageUrl;


    public SaveUserService(final UserRepository userRepository,
        @Value("${profile.base-image-url}") final String baseImageUrl) {
        this.userRepository = userRepository;
        this.baseImageUrl = baseImageUrl;
    }

    public Long invoke(final SaveUserRequest request) {
        final User user = toEntity(request);
        final User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    private User toEntity(final SaveUserRequest request) {
        return new User(request.getUsername(), request.getEmail(), request.getName(),
            new Image(baseImageUrl));
    }
}
