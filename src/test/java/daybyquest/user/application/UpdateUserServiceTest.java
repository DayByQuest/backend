package daybyquest.user.application;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.domain.User;
import daybyquest.user.dto.request.UpdateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class UpdateUserServiceTest extends ServiceTest {

    @Autowired
    private UpdateUserService updateUserService;

    @Test
    void 사용자를_수정한다() {
        // given
        final Long id = ALICE를_저장한다();
        final UpdateUserRequest request = 사용자_수정_요청(BOB.생성());

        // when
        updateUserService.invoke(id, request);

        // then
        final User user = users.getById(id);
        assertAll(() -> {
            assertThat(user.getId()).isEqualTo(id);
            assertThat(user.getUsername()).isEqualTo(BOB.username);
            assertThat(user.getName()).isEqualTo(BOB.name);
            assertThat(user.getEmail()).isEqualTo(ALICE.email);
        });
    }

    @Test
    void 사용자_이름이_중복이면_예외를_던진다() {
        // given
        ALICE를_저장한다();
        final Long id = BOB을_저장한다();
        final UpdateUserRequest request = 사용자_수정_요청(ALICE.생성());

        // when & then
        assertThatThrownBy(() -> updateUserService.invoke(id, request))
                .isInstanceOf(InvalidDomainException.class);
    }

    private UpdateUserRequest 사용자_수정_요청(final User user) {
        final UpdateUserRequest request = new UpdateUserRequest();
        ReflectionTestUtils.setField(request, "username", user.getUsername());
        ReflectionTestUtils.setField(request, "name", user.getName());
        return request;
    }
}
