package daybyquest.group.application;

import static daybyquest.group.domain.GroupUserRole.MEMBER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.group.domain.GroupUser;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SaveMemberServiceTest extends ServiceTest {

    @Autowired
    private SaveMemberService saveMemberService;

    @Test
    void 그룹에_가입한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        interests.save(INTEREST.생성());
        final Long groupId = groups.save(aliceId, GROUP_1.생성()).getId();

        // when
        saveMemberService.invoke(bobId, groupId);
        final GroupUser groupUser = groupUsers.getByUserIdAndGroupId(bobId, groupId);

        // then
        assertAll(() -> {
            assertThat(groupUser.getUserId()).isEqualTo(bobId);
            assertThat(groupUser.getGroupId()).isEqualTo(groupId);
            assertThat(groupUser.getRole()).isEqualTo(MEMBER);
        });
    }
}
