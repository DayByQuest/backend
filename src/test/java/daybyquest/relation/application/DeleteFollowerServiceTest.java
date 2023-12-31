package daybyquest.relation.application;

import static daybyquest.global.error.ExceptionCode.NOT_FOLLOWING_USER;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteFollowerServiceTest extends ServiceTest {

    @Autowired
    private DeleteFollowerService deleteFollowerService;

    @Test
    void 팔로워를_삭제한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        follows.save(new Follow(aliceId, bobId));

        // when
        deleteFollowerService.invoke(bobId, ALICE.username);

        // then
        assertThatThrownBy(() -> follows.getByUserIdAndTargetId(aliceId, bobId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FOLLOWING_USER.getMessage());
    }

    @Test
    void 팔로워가_아닌_대상은_삭제_할_수_없다() {
        // given
        ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();

        // when & then
        assertThatThrownBy(() -> deleteFollowerService.invoke(bobId, ALICE.username))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FOLLOWING_USER.getMessage());
    }
}
