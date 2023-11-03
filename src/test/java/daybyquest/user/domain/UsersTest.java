package daybyquest.user.domain;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistUserException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsersTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Users users;

    @Test
    void 사용자를_저장한다() {
        // given & when
        users.save(ALICE.생성());

        // when & then
        then(userRepository).should().save(any());
    }

    @Test
    void 사용자_저장_시_사용자_이름_중복이_있다면_예외를_던진다() {
        // given
        given(userRepository.existsByUsername(ALICE.username)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> users.save(ALICE.생성()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void ID를_통해_사용자를_조회한다() {
        // given
        final Long aliceId = 1L;
        final User expectedAlice = ALICE.생성(aliceId);
        given(userRepository.findById(aliceId)).willReturn(Optional.of(expectedAlice));

        // when
        final User actual = users.getById(aliceId);

        // when & then
        assertAll(() -> {
            then(userRepository).should().findById(aliceId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expectedAlice);
        });
    }

    @Test
    void ID를_통한_조회_시_사용자가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> users.getById(1L))
                .isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 사용자_이름을_통해_사용자를_조회한다() {
        // given
        final Long aliceId = 1L;
        final User expectedAlice = ALICE.생성(aliceId);
        given(userRepository.findByUsername(ALICE.username)).willReturn(Optional.of(expectedAlice));

        // when
        final User actual = users.getByUsername(ALICE.username);

        // when & then
        assertAll(() -> {
            then(userRepository).should().findByUsername(ALICE.username);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expectedAlice);
        });
    }

    @Test
    void 사용자_이름을_통한_조회_시_사용자가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> users.getByUsername(ALICE.username))
                .isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 사용자_이름을_통해_ID를_조회한다() {
        // given
        final Long expectedId = 1L;
        final User expectedAlice = ALICE.생성(expectedId);
        given(userRepository.findByUsername(ALICE.username)).willReturn(Optional.of(expectedAlice));

        // when
        final Long actual = users.getUserIdByUsername(ALICE.username);

        // when & then
        assertAll(() -> {
            then(userRepository).should().findByUsername(ALICE.username);
            assertThat(actual).isEqualTo(expectedId);
        });
    }

    @Test
    void 사용자_이름을_통한_ID조회_시_사용자가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> users.getUserIdByUsername(ALICE.username))
                .isInstanceOf(NotExistUserException.class);
    }

    @Test
    void 사용자_ID_존재_여부를_검증한다() {
        // given
        final Long aliceId = 1L;
        given(userRepository.existsById(aliceId)).willReturn(true);

        // when
        users.validateExistentById(aliceId);

        // then
        then(userRepository).should().existsById(aliceId);
    }

    @Test
    void 사용자_이름_유일성을_검증한다() {
        // given
        given(userRepository.existsByUsername(ALICE.username)).willReturn(true);

        // when & then
        assertAll(() -> {
            assertThatThrownBy(() -> users.validateUniqueUsername(ALICE.username))
                    .isInstanceOf(InvalidDomainException.class);
            then(userRepository).should().existsByUsername(ALICE.username);
        });
    }

    @Test
    void 사용자_이메일_유일성을_검증한다() {
        // given
        given(userRepository.existsByEmail(ALICE.email)).willReturn(true);

        // when & then
        assertAll(() -> {
            assertThatThrownBy(() -> users.validateUniqueEmail(ALICE.email))
                    .isInstanceOf(InvalidDomainException.class);
            then(userRepository).should().existsByEmail(ALICE.email);
        });
    }
}
