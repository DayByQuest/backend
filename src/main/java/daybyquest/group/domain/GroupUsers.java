package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_MEMBER;
import static daybyquest.global.error.ExceptionCode.NOT_DELETABLE_GROUP_USER;
import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupUserException;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class GroupUsers {

    private final Users users;

    private final Groups groups;

    private final GroupUserRepository groupUserRepository;

    GroupUsers(final Users users, final Groups groups, final GroupUserRepository groupUserRepository) {
        this.users = users;
        this.groups = groups;
        this.groupUserRepository = groupUserRepository;
    }

    public void addUser(final GroupUser groupUser) {
        groups.validateExistentById(groupUser.getGroupId());
        if (groupUser.isManager()) {
            addManager(groupUser);
            return;
        }
        addMember(groupUser);
    }

    private void addMember(final GroupUser groupUser) {
        users.validateExistentById(groupUser.getUserId());
        validateNotMember(groupUser.getUserId(), groupUser.getGroupId());
        groupUserRepository.save(groupUser);
    }

    private void addManager(final GroupUser groupUser) {
        users.validateModeratorById(groupUser.getUserId());
        validateNotMember(groupUser.getUserId(), groupUser.getGroupId());
        groupUserRepository.save(groupUser);
    }

    private void validateNotMember(final Long userId, final Long groupId) {
        if (groupUserRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw new InvalidDomainException(ALREADY_MEMBER);
        }
    }

    public void validateExistentByUserIdAndGroupId(final Long userId, final Long groupId) {
        if (!groupUserRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw new NotExistGroupUserException();
        }
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
