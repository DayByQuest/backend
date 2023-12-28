package daybyquest.group.application;

import static daybyquest.group.domain.GroupUserRole.MANAGER;
import static daybyquest.group.domain.GroupUserRole.MEMBER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_3;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.DARTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.dto.response.GroupUserResponse;
import daybyquest.group.dto.response.PageGroupUsersResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetGroupUsersServiceTest extends ServiceTest {

    @Autowired
    private GetGroupUsersService getGroupUsersService;

    @Test
    void 그룹_회원_목록을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        final Long darthId = DARTH를_저장한다();
        CHARLIE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_3.생성());
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));
        groupUsers.addUser(GroupUser.createGroupMember(darthId, group));

        final List<String> expected = List.of(ALICE.username, BOB.username, DARTH.username);

        // when
        final PageGroupUsersResponse response = getGroupUsersService.invoke(aliceId, group.getId(), 페이지());
        final List<String> actual = response.users().stream().map(GroupUserResponse::username).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 역할이_함께_표시된다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        final Long darthId = DARTH를_저장한다();
        CHARLIE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_3.생성());
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));
        groupUsers.addUser(GroupUser.createGroupMember(darthId, group));

        final List<String> expected = List.of(ALICE.username, BOB.username, DARTH.username);

        // when
        final PageGroupUsersResponse response = getGroupUsersService.invoke(aliceId, group.getId(), 페이지());
        final String aliceRole = response.users().stream().filter(r -> r.username().equals(ALICE.username))
                .findFirst().orElseThrow().role();
        final String bobRole = response.users().stream().filter(r -> r.username().equals(BOB.username))
                .findFirst().orElseThrow().role();

        // then
        assertAll(() -> {
            assertThat(aliceRole).isEqualTo(MANAGER.toString());
            assertThat(bobRole).isEqualTo(MEMBER.toString());
        });
    }
}
