package daybyquest.group.application;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_2;
import static daybyquest.support.fixture.GroupFixtures.GROUP_3;
import static daybyquest.support.fixture.GroupFixtures.GROUP_WITH_ANOTHER_INTEREST;
import static daybyquest.support.fixture.InterestFixtures.ANOTHER_INTEREST;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.PageGroupsResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetGroupsByInterestServiceTest extends ServiceTest {

    @Autowired
    private GetGroupsByInterestService getGroupsByInterestService;

    @Test
    void 관심사를_통해_그룹을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        interests.save(ANOTHER_INTEREST.생성());
        final List<Long> expected = List.of(groups.save(aliceId, GROUP_1.생성()).getId(),
                groups.save(aliceId, GROUP_2.생성()).getId(),
                groups.save(aliceId, GROUP_3.생성()).getId());
        groups.save(aliceId, GROUP_WITH_ANOTHER_INTEREST.생성());

        // when
        final PageGroupsResponse response = getGroupsByInterestService.invoke(aliceId, GROUP_1.interest,
                페이지());
        final List<Long> actual = response.groups().stream().map(GroupResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
