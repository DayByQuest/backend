package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.NOT_DELETABLE_GROUP_USER;
import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;

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

    public void validateGroupManager(final Long userId, final Long groupId) {
        final GroupUser groupUser = getByUserIdAndGroupId(userId, groupId);
        if (!groupUser.isManager()) {
            throw new InvalidDomainException(NOT_GROUP_MANAGER);
        }
    }

    public void delete(final GroupUser groupUser) {
        if (groupUser.isManager()) {
            throw new InvalidDomainException(NOT_DELETABLE_GROUP_USER);
        }
        groupUserRepository.delete(groupUser);
    }
}
