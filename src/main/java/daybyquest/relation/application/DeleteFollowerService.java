package daybyquest.relation.application;

import daybyquest.relation.domain.Follow;
import daybyquest.relation.domain.Follows;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Service;

@Service
public class DeleteFollowerService {

    private final Follows follows;

    private final Users users;

    public DeleteFollowerService(final Follows follows, final Users users) {
        this.follows = follows;
        this.users = users;
    }

    public void invoke(final Long loginId, final String targetUsername) {
        final Long targetId = users.getUserIdByUsername(targetUsername);
        final Follow follow = follows.getByUserIdAndTargetId(targetId, loginId);
        follows.delete(follow);
    }
}
