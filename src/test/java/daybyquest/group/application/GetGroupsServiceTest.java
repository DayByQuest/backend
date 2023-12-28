package daybyquest.group.application;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_2;
import static daybyquest.support.fixture.GroupFixtures.GROUP_3;
import static daybyquest.support.fixture.GroupFixtures.GROUP_4;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.group.dto.response.MultipleGroupsResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetGroupsServiceTest extends ServiceTest {

    @Autowired
    private GetGroupsService getGroupsService;

    @Test
    void 가입한_그룹_목록을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(bobId, GROUP_3.생성());
        groupUsers.addUser(GroupUser.createGroupMember(aliceId, group));

        final List<Long> expected = List.of(groups.save(aliceId, GROUP_1.생성()).getId(),
                groups.save(aliceId, GROUP_2.생성()).getId(),
                group.getId());
        groups.save(bobId, GROUP_4.생성());

        // when
        final MultipleGroupsResponse response = getGroupsService.invoke(aliceId);
        final List<Long> actual = response.groups().stream().map(GroupResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
