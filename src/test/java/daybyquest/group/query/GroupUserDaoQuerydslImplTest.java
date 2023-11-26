package daybyquest.group.query;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DAVID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.query.LongIdList;
import daybyquest.global.query.NoOffsetIdPage;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(GroupUserDaoQuerydslImpl.class)
public class GroupUserDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private GroupUserDaoQuerydslImpl groupUserDao;

    @Test
    void 그룹원의_사용자_ID_목록을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());
        final Group group = 저장한다(GROUP_1.생성());
        저장한다(GroupUser.createGroupMember(bob.getId(), group));
        저장한다(GroupUser.createGroupMember(alice.getId(), group));
        저장한다(GroupUser.createGroupMember(charlie.getId(), group));

        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);
        final List<Long> expected = List.of(bob.getId(), alice.getId(), charlie.getId());

        // when
        final LongIdList ids = groupUserDao.findUserIdsByGroupId(group.getId(), page);
        final List<Long> actual = ids.getIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void ID_컬렉션으로_그룹_회원_목록을_조회한다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());
        final Group group = 저장한다(GROUP_1.생성());
        저장한다(GroupUser.createGroupManager(bob.getId(), group));
        저장한다(GroupUser.createGroupMember(alice.getId(), group));
        저장한다(GroupUser.createGroupMember(charlie.getId(), group));

        final List<Long> expectedIds = List.of(bob.getId(), alice.getId(), charlie.getId());
        final List<String> expectedRoles = List.of("MANAGER", "MEMBER", "MEMBER");

        // when
        final List<GroupUserData> groupUserData = groupUserDao.findAllByUserIdsIn(group.getId(), expectedIds);
        final List<Long> actualIds = groupUserData.stream().map(GroupUserData::getId).toList();
        final List<String> actualRoles = groupUserData.stream().map(GroupUserData::getRole).toList();

        // then
        assertAll(() -> {
            assertThat(groupUserData).hasSize(3);
            assertThat(actualIds).containsExactlyInAnyOrderElementsOf(expectedIds);
            assertThat(actualRoles).containsExactlyInAnyOrderElementsOf(expectedRoles);
        });
    }
}
