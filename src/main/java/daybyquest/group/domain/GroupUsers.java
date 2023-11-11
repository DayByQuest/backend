package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.NOT_DELETABLE_GROUP_USER;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupUserException;
import org.springframework.stereotype.Component;

@Component
public class GroupUsers {

    private final GroupUserRepository groupUserRepository;

    GroupUsers(final GroupUserRepository groupUserRepository) {
        this.groupUserRepository = groupUserRepository;
    }

    public GroupUser getByUserIdAndGroupId(final Long userId, final Long groupId) {
        return groupUserRepository.findByUserIdAndGroupId(userId, groupId)
                .orElseThrow(NotExistGroupUserException::new);
    }

    public void delete(final GroupUser groupUser) {
        if (groupUser.isManager()) {
            throw new InvalidDomainException(NOT_DELETABLE_GROUP_USER);
        }
        groupUserRepository.delete(groupUser);
    }
}
