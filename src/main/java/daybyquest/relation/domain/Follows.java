package daybyquest.relation.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_FOLLOWING_USER;
import static daybyquest.global.error.ExceptionCode.NOT_FOLLOWING_USER;

import daybyquest.global.error.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class Follows {

    private final FollowRepository followRepository;

    Follows(final FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    public void save(final Follow follow) {
        validateNonExistent(follow);
        followRepository.save(follow);
    }

    private void validateNonExistent(final Follow follow) {
        if (followRepository.existsByUserIdAndTargetId(follow.getUserId(), follow.getTargetId())) {
            throw new BadRequestException(ALREADY_FOLLOWING_USER);
        }
    }

    public Follow getByUserIdAndTargetId(final Long userId, final Long targetId) {
        return followRepository.findByUserIdAndTargetId(userId, targetId).orElseThrow(
                () -> new BadRequestException(NOT_FOLLOWING_USER));
    }

    public void delete(final Follow follow) {
        followRepository.delete(follow);
    }
}
