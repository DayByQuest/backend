package daybyquest.user.domain;

import static daybyquest.support.fixture.UserFixtures.BOB;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class UserTest {

    @Nested
    class 생성자는 {

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

    @Nested
    class 객체_변경은 {

        @ParameterizedTest
        @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육"})
        void 사용자_이름이_1_에서_15_글자_사이가_아니면_예외를_던진다(final String username) {
            // given
            final User user = BOB.생성();

            // when & then
            assertThatThrownBy(() -> user.updateUsername(username))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육칠팔구십일"})
        void 이름이_1_에서_20_글자_사이가_아니면_예외를_던진다(final String name) {
            // given
            final User user = BOB.생성();

            // when & then
            assertThatThrownBy(() -> user.updateName(name))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 관심사가_5_개_보다_많으면_예외를_던진다() {
            // given
            final User user = BOB.생성();
            final List<String> interests = List.of("관심사1", "관심사2", "관심사3", "관심사4", "관심사5", "관심사6");

            // when & then
            assertThatThrownBy(() -> user.updateInterests(interests))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }
}
