package daybyquest.user.application;

import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.profile.domain.ProfileSetting;
import daybyquest.profile.domain.ProfileSettings;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.domain.User;
import daybyquest.user.dto.request.SaveUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class SaveUserServiceTest extends ServiceTest {

    @Autowired
    private SaveUserService saveUserService;

    @Autowired
    private ProfileSettings profileSettings;

    @Test
    void 사용자를_저장한다() {
        // given
        final SaveUserRequest request = 회원가입_요청(ALICE.생성());

        // when
        final Long id = saveUserService.invoke(request);

        // then
        final User user = users.getById(id);
        final ProfileSetting profileSetting = profileSettings.getById(id);
        assertAll(() -> {
            assertThat(user.getId()).isEqualTo(id);
            assertThat(user.getUsername()).isEqualTo(ALICE.username);
            assertThat(user.getName()).isEqualTo(ALICE.name);
            assertThat(user.getEmail()).isEqualTo(ALICE.email);
            assertThat(profileSetting.getUserId()).isEqualTo(id);
        });
    }

    @Test
    void 사용자_이름이_중복이면_예외를_던진다() {
        // given
        ALICE를_저장한다();
        final SaveUserRequest request = 회원가입_요청(ALICE.생성());

        // when & then
        assertThatThrownBy(() -> saveUserService.invoke(request))
                .isInstanceOf(InvalidDomainException.class);
    }

    private SaveUserRequest 회원가입_요청(final User user) {
        final SaveUserRequest request = new SaveUserRequest();
        ReflectionTestUtils.setField(request, "username", user.getUsername());
        ReflectionTestUtils.setField(request, "email", user.getEmail());
        ReflectionTestUtils.setField(request, "name", user.getName());
        return request;
    }
}
