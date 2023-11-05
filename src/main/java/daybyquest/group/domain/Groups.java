package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_MEMBER;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.interest.domain.Interests;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Groups {

    private final GroupRepository groupRepository;

    private final GroupUserRepository groupUserRepository;

    private final Users users;

    private final Interests interests;

    Groups(final GroupRepository groupRepository, final GroupUserRepository groupUserRepository,
            final Users users,
            final Interests interests) {
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
        this.users = users;
        this.interests = interests;
    }

    public Long save(final Long userId, final Group group) {
        users.validateModeratorById(userId);
        interests.validateInterest(group.getInterest());
        final Group savedGroup = groupRepository.save(group);
        groupUserRepository.save(GroupUser.createGroupManager(userId, savedGroup));
        return savedGroup.getId();
    }

    public void addUser(final GroupUser groupUser) {
        validateExistentById(groupUser.getGroupId());
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

    public void validateExistentById(final Long id) {
        if (!groupRepository.existsById(id)) {
            throw new NotExistGroupException();
        }
    }

    private void validateNotMember(final Long userId, final Long groupId) {
        if (groupUserRepository.existsByUserIdAndGroupId(userId, groupId)) {
            throw new InvalidDomainException(ALREADY_MEMBER);
        }
    }
}
