package daybyquest.interest.application;

import static daybyquest.support.fixture.InterestFixtures.INTEREST_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST_2;
import static daybyquest.support.fixture.InterestFixtures.INTEREST_3;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.interest.dto.response.InterestResponse;
import daybyquest.interest.dto.response.MultiInterestResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetInterestsServiceTest extends ServiceTest {

    @Autowired
    private GetInterestsService getInterestsService;

    @Test
    void 관심사들을_조회한다() {
        // given
        interests.save(INTEREST_1.생성());
        interests.save(INTEREST_2.생성());
        interests.save(INTEREST_3.생성());
        final List<String> expected = List.of(INTEREST_1.name, INTEREST_2.name, INTEREST_3.name);

        // when
        final MultiInterestResponse response = getInterestsService.invoke();
        final List<String> actual = response.interests().stream().map(InterestResponse::name)
                .toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
