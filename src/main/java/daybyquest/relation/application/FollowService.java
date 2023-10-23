package daybyquest.relation.application;

import daybyquest.relation.domain.Follow;
import daybyquest.relation.domain.Follows;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final Users users;

    private final Follows follows;

    public FollowService(final Users users, final Follows follows) {
        this.users = users;
        this.follows = follows;
    }

    @Transactional
    public void invoke(final Long loginId, final String targetUsername) {
        users.validateExistentById(loginId);
        final Long targetId = users.getUserIdByUsername(targetUsername);
        final Follow follow = new Follow(loginId, targetId);
        follows.save(follow);
    }
}
