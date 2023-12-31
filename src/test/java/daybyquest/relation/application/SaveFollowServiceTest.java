package daybyquest.relation.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_FOLLOWING_USER;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.relation.domain.Follow;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveFollowServiceTest extends ServiceTest {

    @Autowired
    private SaveFollowService saveFollowService;

    @Test
    void 팔로우를_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();

        // when
        saveFollowService.invoke(aliceId, BOB.username);

        // then
        final Follow follow = follows.getByUserIdAndTargetId(aliceId, bobId);
        assertAll(() -> {
            assertThat(follow.getUserId()).isEqualTo(aliceId);
            assertThat(follow.getTargetId()).isEqualTo(bobId);
        });
    }

    @Test
    void 이미_팔로우한_대상은_팔로우_할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        follows.save(new Follow(aliceId, bobId));

        // when & then
        assertThatThrownBy(() -> saveFollowService.invoke(aliceId, BOB.username))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_FOLLOWING_USER.getMessage());
    }
}
