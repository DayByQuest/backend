package daybyquest.relation.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_FOLLOWING_USER;
import static daybyquest.global.error.ExceptionCode.NOT_FOLLOWING_USER;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.user.domain.Users;
import org.springframework.stereotype.Component;

@Component
public class Follows {

    private final FollowRepository followRepository;

    private final Users users;

    Follows(final FollowRepository followRepository, final Users users) {
        this.followRepository = followRepository;
        this.users = users;
    }

    public void save(final Follow follow) {
        users.validateExistentById(follow.getUserId());
        users.validateExistentById(follow.getTargetId());
        validateNonExistent(follow);
        followRepository.save(follow);
    }

    private void validateNonExistent(final Follow follow) {
        if (followRepository.existsByUserIdAndTargetId(follow.getUserId(), follow.getTargetId())) {
            throw new InvalidDomainException(ALREADY_FOLLOWING_USER);
        }
    }

    public Follow getByUserIdAndTargetId(final Long userId, final Long targetId) {
        return followRepository.findByUserIdAndTargetId(userId, targetId).orElseThrow(
                () -> new InvalidDomainException(NOT_FOLLOWING_USER));
    }

    public void delete(final Follow follow) {
        followRepository.delete(follow);
    }
}
