package daybyquest.relation.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_FOLLOWING_USER;
import static daybyquest.global.error.ExceptionCode.FOLLOW_MYSELF;
import static daybyquest.global.error.ExceptionCode.NOT_FOLLOWING_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.user.domain.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FollowsTest {

    @Mock
    private FollowRepository followRepository;

    @Mock
    private Users users;

    @InjectMocks
    private Follows follows;

    @Test
    void 사용자_ID를_검증하고_팔로우를_저장한다() {
        // given
        final Long aliceId = 1L;
        final Long targetId = 2L;

        // when
        follows.save(new Follow(aliceId, targetId));

        // then
        assertAll(() -> {
            then(users).should().validateExistentById(aliceId);
            then(users).should().validateExistentById(targetId);
            then(followRepository).should().save(any(Follow.class));
        });
    }

    @Test
    void 저장_시_이미_팔로우_중이라면_예외를_던진다() {
        // given
        final Long aliceId = 1L;
        final Long targetId = 2L;
        given(followRepository.existsByUserIdAndTargetId(aliceId, targetId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> follows.save(new Follow(aliceId, targetId)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_FOLLOWING_USER.getMessage());
    }

    @Test
    void 저장_시_자기_자신을_대상으로_한다면_에외를_던진다() {
        // given
        final Long aliceId = 1L;

        // when & then
        assertThatThrownBy(() -> follows.save(new Follow(aliceId, aliceId)))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(FOLLOW_MYSELF.getMessage());
    }

    @Test
    void 사용자_ID와_대상_ID를_통해_팔로우를_조회한다() {
        // given
        final Long aliceId = 1L;
        final Long targetId = 2L;
        final Follow follow = new Follow(aliceId, targetId);
        given(followRepository.findByUserIdAndTargetId(aliceId, targetId)).willReturn(Optional.of(follow));

        // when
        final Follow actual = follows.getByUserIdAndTargetId(aliceId, targetId);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(follow);
    }

    @Test
    void 사용자_ID와_대상_ID를_통한_조회_시_팔로우가_없다면_예외를_던진다() {
        // given
        final Long aliceId = 1L;
        final Long targetId = 2L;

        // when & then
        assertThatThrownBy(() -> follows.getByUserIdAndTargetId(aliceId, targetId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FOLLOWING_USER.getMessage());
    }

    @Test
    void 팔로우를_삭제한다() {
        // given
        final Long aliceId = 1L;
        final Long targetId = 2L;
        final Follow follow = new Follow(aliceId, targetId);

        // when
        follows.delete(follow);

        // then
        then(followRepository).should().delete(follow);
    }
}
