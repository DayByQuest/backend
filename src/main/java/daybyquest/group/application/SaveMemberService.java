package daybyquest.group.application;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.domain.Groups;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveMemberService {

    private final Groups groups;

    public SaveMemberService(final Groups groups) {
        this.groups = groups;
    }

    @Transactional
    public void invoke(final Long loginId, final Long groupId) {
        final Group group = groups.getById(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(loginId, group);
        groups.addUser(groupUser);
    }
}
