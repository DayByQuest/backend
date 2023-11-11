package daybyquest.group.application;

import daybyquest.group.domain.GroupUser;
import daybyquest.group.domain.GroupUsers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMemberService {

    private final GroupUsers groupUsers;

    public DeleteMemberService(final GroupUsers groupUsers) {
        this.groupUsers = groupUsers;
    }

    @Transactional
    public void invoke(final Long loginId, final Long groupId) {
        final GroupUser groupUser = groupUsers.getByUserIdAndGroupId(loginId, groupId);
        groupUsers.delete(groupUser);
    }
}
