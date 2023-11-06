package daybyquest.user.application;

import daybyquest.image.domain.BaseImageProperties;
import daybyquest.image.domain.Image;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserSavedEvent;
import daybyquest.user.domain.Users;
import daybyquest.user.dto.request.SaveUserRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveUserService {

    private final Users users;

    private final BaseImageProperties baseImageProperties;

    private final ApplicationEventPublisher publisher;

    public SaveUserService(final Users users, final BaseImageProperties baseImageProperties,
            final ApplicationEventPublisher publisher) {
        this.users = users;
        this.baseImageProperties = baseImageProperties;
        this.publisher = publisher;
    }

    @Transactional
    public Long invoke(final SaveUserRequest request) {
        final User savedUser = users.save(toEntity(request));
        publisher.publishEvent(new UserSavedEvent(savedUser.getId()));
        return savedUser.getId();
    }

    private User toEntity(final SaveUserRequest request) {
        return new User(request.getUsername(), request.getEmail(), request.getName(),
                new Image(baseImageProperties.getUserIdentifier()));
    }
}
