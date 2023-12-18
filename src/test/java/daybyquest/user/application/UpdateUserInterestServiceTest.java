package daybyquest.user.application;

import static daybyquest.support.fixture.InterestFixtures.INTEREST_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.NotExistInterestException;
import daybyquest.interest.domain.Interests;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.request.UpdateUserInterestRequest;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class UpdateUserInterestServiceTest extends ServiceTest {

    @Autowired
    private UpdateUserInterestService updateUserInterestService;

    @Autowired
    private Interests interests;

    @Test
    void 사용자_관심사를_수정한다() {
        // given
        final Long id = ALICE를_저장한다();
        interests.save(INTEREST_1.생성());
        interests.save(INTEREST_2.생성());
        final Set<String> expected = Set.of(INTEREST_1.name, INTEREST_2.name);
        final UpdateUserInterestRequest request = 사용자_관심사_수정_요청(expected);

        // when
        updateUserInterestService.invoke(id, request);

        // then
        final List<String> actual = users.getById(id).getInterests();
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 존재하지_않는_관심사를_지정하면_예외를_던진다() {
        // given
        final Long id = ALICE를_저장한다();
        interests.save(INTEREST_1.생성());
        final Set<String> interests = Set.of(INTEREST_1.name, INTEREST_2.name);
        final UpdateUserInterestRequest request = 사용자_관심사_수정_요청(interests);

        // when & then
        assertThatThrownBy(() -> updateUserInterestService.invoke(id, request))
                .isInstanceOf(NotExistInterestException.class);
    }

    private UpdateUserInterestRequest 사용자_관심사_수정_요청(final Set<String> interests) {
        final UpdateUserInterestRequest request = new UpdateUserInterestRequest();
        ReflectionTestUtils.setField(request, "interests", interests);
        return request;
    }
}
