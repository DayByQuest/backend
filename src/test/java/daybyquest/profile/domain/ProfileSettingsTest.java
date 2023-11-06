package daybyquest.profile.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.NotExistUserException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProfileSettingsTest {

    @Mock
    private ProfileSettingRepository profileSettingRepository;

    @InjectMocks
    private ProfileSettings profileSettings;

    @Test
    void 프로필_설정을_저장한다() {
        // given & when
        profileSettings.save(new ProfileSetting(1L));

        // then
        then(profileSettingRepository).should().save(any(ProfileSetting.class));
    }

    @Test
    void 사용자_ID를_통해_프로필_설정을_조회한다() {
        // given
        final Long userId = 1L;
        final ProfileSetting expected = new ProfileSetting(userId);
        given(profileSettingRepository.findByUserId(userId))
                .willReturn(Optional.of(expected));

        // when
        final ProfileSetting actual = profileSettings.getById(userId);

        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void 사용자_ID를_통한_조회_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> profileSettings.getById(1L))
                .isInstanceOf(NotExistUserException.class);
    }
}
