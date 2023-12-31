package daybyquest.relation.application;

import static daybyquest.global.error.ExceptionCode.NOT_FOLLOWING_USER;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteFollowServiceTest extends ServiceTest {

    @Autowired
    private DeleteFollowService deleteFollowService;

    @Test
    void 팔로우를_취소한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        follows.save(new Follow(aliceId, bobId));

        // when
        deleteFollowService.invoke(aliceId, BOB.username);

        // then
        assertThatThrownBy(() -> follows.getByUserIdAndTargetId(aliceId, bobId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FOLLOWING_USER.getMessage());
    }

    @Test
    void 팔로우하지_않은_대상은_취소_할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        BOB을_저장한다();

        // when & then
        assertThatThrownBy(() -> deleteFollowService.invoke(aliceId, BOB.username))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_FOLLOWING_USER.getMessage());
    }
}
