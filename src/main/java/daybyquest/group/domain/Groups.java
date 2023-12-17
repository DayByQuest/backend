package daybyquest.group.domain;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_GROUP_NAME;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.interest.domain.Interests;
import org.springframework.stereotype.Component;

@Component
public class Groups {

    private final GroupRepository groupRepository;

    private final GroupUsers groupUsers;

    private final Interests interests;

    Groups(final GroupRepository groupRepository, final GroupUsers groupUsers, final Interests interests) {
        this.groupRepository = groupRepository;
        this.groupUsers = groupUsers;
        this.interests = interests;
    }

    public Group save(final Long userId, final Group group) {
        interests.validateInterest(group.getInterest());
        validateNotExistentByName(group.getName());
        final Group savedGroup = groupRepository.save(group);
        groupUsers.addUser(GroupUser.createGroupManager(userId, savedGroup));
        return savedGroup;
    }

    public void validateNotExistentByName(final String name) {
        if (groupRepository.existsByName(name)) {
            throw new InvalidDomainException(DUPLICATED_GROUP_NAME);
        }
    }

    public void validateExistentById(final Long id) {
        if (!groupRepository.existsById(id)) {
            throw new NotExistGroupException();
        }
    }

    public Group getById(final Long id) {
        return groupRepository.findById(id).orElseThrow(NotExistGroupException::new);
    }
}
