package daybyquest.user.application;

import daybyquest.image.vo.BaseImageProperties;
import daybyquest.image.vo.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveUserService {

    private final Users users;

    private final BaseImageProperties baseImageProperties;

    public SaveUserService(final Users users, final BaseImageProperties baseImageProperties) {
        this.users = users;
        this.baseImageProperties = baseImageProperties;
    }

    @Transactional
    public Long invoke(final SaveUserRequest request) {
        final User user = toEntity(request);
        final User savedUser = users.save(user);
        return savedUser.getId();
    }

    private User toEntity(final SaveUserRequest request) {
        return new User(request.getUsername(), request.getEmail(), request.getName(),
                new Image(baseImageProperties.getUserIdentifier()));
    }
}
