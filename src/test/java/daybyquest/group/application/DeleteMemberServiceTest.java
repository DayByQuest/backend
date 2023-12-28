package daybyquest.group.application;

import static daybyquest.global.error.ExceptionCode.NOT_DELETABLE_GROUP_USER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupUserException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class DeleteMemberServiceTest extends ServiceTest {

    @Autowired
    private DeleteMemberService deleteMemberService;

    @Test
    void 그룹에_탈퇴한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        interests.save(INTEREST.생성());
        final Group group = groups.save(aliceId, GROUP_1.생성());
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        // when
        deleteMemberService.invoke(bobId, group.getId());

        // then
        assertThatThrownBy(() -> groupUsers.validateExistentByUserIdAndGroupId(bobId, group.getId()))
                .isInstanceOf(NotExistGroupUserException.class);
    }

    @Test
    void 그룹_관리자는_탈퇴할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        final Long groupId = groups.save(aliceId, GROUP_1.생성()).getId();

        // when & then
        assertThatThrownBy(() -> deleteMemberService.invoke(aliceId, groupId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_DELETABLE_GROUP_USER.getMessage());
    }
}
