package daybyquest.user.domain;

import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육"})
    void 사용자_이름이_1_에서_15_글자_사이가_아니면_예외를_던진다(final String username) {
        // given & when & then
        assertThatThrownBy(() -> new User(username, BOB.email, BOB.name, BOB.프로필_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육칠팔구십일이삼사오육칠팔구십@email.com"})
    void 이메일이_1_에서_20_글자_사이가_아니면_예외를_던진다(final String email) {
        // given & when & then
        assertThatThrownBy(() -> new User(BOB.username, email, BOB.name, BOB.프로필_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"aliceemail.com", "alice@emailcom", "alice@", "@email.com", "email.com"})
    void 이메일_형식이_맞지_않으면_예외를_던진다(final String email) {
        // given & when & then
        assertThatThrownBy(() -> new User(BOB.username, email, BOB.name, BOB.프로필_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육칠팔구십일"})
    void 이름이_1_에서_20_글자_사이가_아니면_예외를_던진다(final String name) {
        // given & when & then
        assertThatThrownBy(() -> new User(BOB.username, BOB.email, name, BOB.프로필_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }
}
