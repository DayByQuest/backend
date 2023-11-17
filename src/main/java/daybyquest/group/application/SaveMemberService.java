package daybyquest.group.application;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.domain.GroupUsers;
import daybyquest.group.domain.Groups;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveMemberService {

    private final Groups groups;

    private final GroupUsers groupUsers;

    public SaveMemberService(final Groups groups, final GroupUsers groupUsers) {
        this.groups = groups;
        this.groupUsers = groupUsers;
    }

    @Transactional
    public void invoke(final Long loginId, final Long groupId) {
        final Group group = groups.getById(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(loginId, group);
        groupUsers.addUser(groupUser);
    }
}
