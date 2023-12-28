package daybyquest.group.application;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_GROUP_NAME;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CheckGroupNameServiceTest extends ServiceTest {

    @Autowired
    private CheckGroupNameService checkGroupNameService;

    @Test
    void 그룹_이름_중복을_검사한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        groups.save(aliceId, GROUP_1.생성());
        ;

        // when & then
        assertThatThrownBy(() -> checkGroupNameService.invoke(GROUP_1.name))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(DUPLICATED_GROUP_NAME.getMessage());
    }
}
