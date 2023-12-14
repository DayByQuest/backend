package daybyquest.user.application;

import static daybyquest.global.error.ExceptionCode.INVALID_VISIBILITY;
import static daybyquest.user.domain.UserVisibility.PRIVATE;
import static daybyquest.user.domain.UserVisibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.domain.User;
import daybyquest.user.dto.request.UpdateUserVisibilityRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class UpdateVisibilityServiceTest extends ServiceTest {

    @Autowired
    private UpdateVisibilityService updateVisibilityService;

    @Test
    void 사용자를_공개_상태로_만든다() {
        // given
        final Long id = ALICE를_저장한다();
        final UpdateUserVisibilityRequest request = 사용자_가시성_수정_요청(PUBLIC.toString());

        // when
        updateVisibilityService.invoke(id, request);

        // then
        final User user = users.getById(id);
        assertThat(user.getVisibility()).isEqualTo(PUBLIC);
    }

    @Test
    void 사용자를_비공개_상태로_만든다() {
        // given
        final Long id = ALICE를_저장한다();
        final UpdateUserVisibilityRequest request = 사용자_가시성_수정_요청(PRIVATE.toString());

        // when
        updateVisibilityService.invoke(id, request);

        // then
        final User user = users.getById(id);
        assertThat(user.getVisibility()).isEqualTo(PRIVATE);
    }

    @Test
    void 올바르지_않은_문자열을_입력하면_예외를_던진다() {
        // given
        final Long id = ALICE를_저장한다();
        final UpdateUserVisibilityRequest request = 사용자_가시성_수정_요청("ASDASD");

        // when & then
        assertThatThrownBy(() -> updateVisibilityService.invoke(id, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(INVALID_VISIBILITY.getMessage());
    }

    private UpdateUserVisibilityRequest 사용자_가시성_수정_요청(final String visibility) {
        final UpdateUserVisibilityRequest request = new UpdateUserVisibilityRequest();
        ReflectionTestUtils.setField(request, "visibility", visibility);
        return request;
    }
}
