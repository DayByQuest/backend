package daybyquest.user.application;

import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckUsernameService {

    private final Users users;

    public CheckUsernameService(final Users users) {
        this.users = users;
    }

    @Transactional(readOnly = true)
    public void invoke(final String username) {
        users.validateUniqueUsername(username);
    }
}
