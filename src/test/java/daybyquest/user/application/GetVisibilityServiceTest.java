package daybyquest.user.application;

import static daybyquest.user.domain.UserVisibility.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.response.UserVisibilityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetVisibilityServiceTest extends ServiceTest {

    @Autowired
    private GetVisibilityService getVisibilityService;

    @Test
    void 사용자_가시성을_조회한다() {
        // given
        final Long id = ALICE를_저장한다();

        // when
        final UserVisibilityResponse response = getVisibilityService.invoke(id);

        // then
        assertThat(response.visibility()).isEqualTo(PUBLIC.toString());
    }
}
