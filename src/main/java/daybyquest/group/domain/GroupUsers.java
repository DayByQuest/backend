package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_MEMBER;
import static daybyquest.global.error.ExceptionCode.EXCEED_MAX_GROUP;
import static daybyquest.global.error.ExceptionCode.EXCEED_MAX_GROUP_MEMBER;
import static daybyquest.global.error.ExceptionCode.NOT_DELETABLE_GROUP_USER;
import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.global.error.exception.NotExistGroupUserException;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class GroupUsers {

    private static final int MAX_GROUP_MEMBER_COUNT = 100;

    private static final int MAX_REGISTERED_GROUP_COUNT = 10;

    private final Users users;

    private final GroupRepository groupRepository;

    private final GroupUserRepository groupUserRepository;

    GroupUsers(final Users users, final GroupRepository groupRepository,
            final GroupUserRepository groupUserRepository) {
        this.users = users;
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
    }

    public void addUser(final GroupUser groupUser) {
        validateGroup(groupUser.getGroupId());
        validateUser(groupUser);
        validateNotMember(groupUser.getUserId(), groupUser.getGroupId());
        validateMaxCount(groupUser.getUserId(), groupUser.getGroupId());
        groupUserRepository.save(groupUser);
    }

    private void validateGroup(final Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new NotExistGroupException();
        }
    }

    private void validateUser(final GroupUser groupUser) {
        if (groupUser.isManager()) {
            users.validateModeratorById(groupUser.getUserId());
            return;
        }
        users.validateExistentById(groupUser.getUserId());
    }

    private void validateNotMember(final Long userId, final Long groupId) {
        if (groupUserRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw new InvalidDomainException(ALREADY_MEMBER);
        }
    }

    private void validateMaxCount(final Long userId, final Long groupId) {
        if (groupUserRepository.countByGroupId(groupId) >= MAX_GROUP_MEMBER_COUNT) {
            throw new InvalidDomainException(EXCEED_MAX_GROUP_MEMBER);
        }
        if (groupUserRepository.countByUserId(userId) >= MAX_REGISTERED_GROUP_COUNT) {
            throw new InvalidDomainException(EXCEED_MAX_GROUP);
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
