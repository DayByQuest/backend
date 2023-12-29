package daybyquest.interest.application;

import static daybyquest.support.fixture.InterestFixtures.INTEREST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.interest.domain.Interest;
import daybyquest.interest.dto.request.SaveInterestRequest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveInterestServiceTest extends ServiceTest {

    @Autowired
    private SaveInterestService saveInterestService;

    @Test
    void 관심사를_생성한다() {
        // given
        final SaveInterestRequest request = 관심사_생성_요청(INTEREST_1.생성());
        final MultipartFile file = 사진_파일(INTEREST_1.imageIdentifier);

        // when
        saveInterestService.invoke(request, file);
        final Interest actual = interests.findAll().stream()
                .filter(interest -> interest.getName().equals(INTEREST_1.name))
                .findFirst().orElseThrow();

        // then
        assertAll(() -> {
            assertThat(actual.getName()).isEqualTo(INTEREST_1.name);
            assertThat(actual.getImageIdentifier()).isEqualTo(INTEREST_1.imageIdentifier);
        });
    }

    private SaveInterestRequest 관심사_생성_요청(final Interest interest) {
        final SaveInterestRequest request = new SaveInterestRequest();
        ReflectionTestUtils.setField(request, "name", interest.getName());
        return request;
    }
}
