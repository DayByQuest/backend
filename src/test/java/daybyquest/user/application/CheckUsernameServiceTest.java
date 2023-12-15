package daybyquest.user.application;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_USERNAME;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckUsernameServiceTest extends ServiceTest {

    @Autowired
    private CheckUsernameService checkUsernameService;

    @Test
    void 사용자_이름_중복을_검사한다() {
        // given & when & then
        assertThatCode(() -> checkUsernameService.invoke(ALICE.username))
                .doesNotThrowAnyException();
    }

    @Test
    void 사용자_이름이_이미_있다면_예외를_던진다() {
        // given
        ALICE를_저장한다();

        // when & then
        assertThatThrownBy(() -> checkUsernameService.invoke(ALICE.username))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(DUPLICATED_USERNAME.getMessage());
    }
}
