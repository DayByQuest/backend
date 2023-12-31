package daybyquest.group.query;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_2;
import static daybyquest.support.fixture.GroupFixtures.GROUP_3;
import static daybyquest.support.fixture.GroupFixtures.GROUP_4;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.NotExistGroupException;
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

@Import(GroupDaoQuerydslImpl.class)
public class GroupDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private GroupDaoQuerydslImpl groupDao;

    @Test
    void 그룹_ID로_데이터를_조회한다() {
        // given
        final User user = 저장한다(BOB.생성());
        final Group group = 저장한다(GROUP_1.생성());

        // when
        final GroupData groupData = groupDao.getById(user.getId(), group.getId());

        // then
        assertAll(() -> {
            assertThat(groupData.getInterest()).isEqualTo(GROUP_1.interest);
            assertThat(groupData.getName()).isEqualTo(GROUP_1.name);
            assertThat(groupData.getDescription()).isEqualTo(GROUP_1.description);
            assertThat(groupData.getImageIdentifier()).isEqualTo(GROUP_1.imageIdentifier);
            assertThat(groupData.getUserCount()).isZero();
            assertThat(groupData.isGroupManager()).isFalse();
        });
    }

    @Test
    void 그룹_인원_수가_함께_조회된다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final Group group = 저장한다(GROUP_1.생성());

        저장한다(GroupUser.createGroupMember(bob.getId(), group));
        저장한다(GroupUser.createGroupMember(alice.getId(), group));
        저장한다(GroupUser.createGroupManager(charlie.getId(), group));

        // when
        final GroupData groupData = groupDao.getById(bob.getId(), group.getId());

        // then
        assertThat(groupData.getUserCount()).isEqualTo(3);
    }

    @Test
    void 그룹에_속했는지_여부가_함께_조회된다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final User charile = 저장한다(CHARLIE.생성());
        final Group group = 저장한다(GROUP_1.생성());

        저장한다(GroupUser.createGroupMember(bob.getId(), group));
        저장한다(GroupUser.createGroupManager(alice.getId(), group));

        // when
        final GroupData bobActual = groupDao.getById(bob.getId(), group.getId());
        final GroupData aliceAtual = groupDao.getById(alice.getId(), group.getId());
        final GroupData charlieActual = groupDao.getById(charile.getId(), group.getId());

        // then
        assertAll(() -> {
            assertThat(bobActual.isGroupMember()).isTrue();
            assertThat(aliceAtual.isGroupMember()).isTrue();
            assertThat(charlieActual.isGroupMember()).isFalse();
        });
    }

    @Test
    void 그룹_관리자_여부가_함께_조회된다() {
        // given
        final User bob = 저장한다(BOB.생성());
        final User alice = 저장한다(ALICE.생성());
        final Group group = 저장한다(GROUP_1.생성());

        저장한다(GroupUser.createGroupMember(bob.getId(), group));
        저장한다(GroupUser.createGroupManager(alice.getId(), group));

        // when
        final GroupData bobActual = groupDao.getById(bob.getId(), group.getId());
        final GroupData aliceAtual = groupDao.getById(alice.getId(), group.getId());

        // then
        assertAll(() -> {
            assertThat(bobActual.isGroupManager()).isFalse();
            assertThat(aliceAtual.isGroupManager()).isTrue();
        });
    }

    @Test
    void 그룹ID가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> groupDao.getById(1L, 2L))
                .isInstanceOf(NotExistGroupException.class);
    }

    @Test
    void 사용자_ID로_가입한_그룹_목록을_조회한다() {
        // given
        final User user = 저장한다(BOB.생성());
        final Group group1 = 저장한다(GROUP_1.생성());
        final Group group2 = 저장한다(GROUP_2.생성());
        저장한다(GROUP_3.생성());
        저장한다(GroupUser.createGroupMember(user.getId(), group1));
        저장한다(GroupUser.createGroupMember(user.getId(), group2));

        final List<Long> expected = List.of(group1.getId(), group2.getId());

        // when
        final List<GroupData> groupData = groupDao.findAllByUserId(user.getId());
        final List<Long> actual = groupData.stream().map(GroupData::getId).toList();

        // then
        assertAll(() -> {
            assertThat(groupData).hasSize(2);
            assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
        });
    }

    @Test
    void ID_컬렉션으로_그룹_목록을_조회한다() {
        // given
        final Long userId = 1L;
        final Group group1 = 저장한다(GROUP_1.생성());
        final Group group2 = 저장한다(GROUP_2.생성());
        final Group group3 = 저장한다(GROUP_3.생성());
        저장한다(GROUP_4.생성());

        final List<Long> expected = List.of(group1.getId(), group2.getId(), group3.getId());

        // when
        final List<GroupData> groupData = groupDao.findAllByIdsIn(userId, expected);
        final List<Long> actual = groupData.stream().map(GroupData::getId).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 그룹_이름으로_검색한다() {
        // given
        final Group group1 = 저장한다(GROUP_1.생성());
        final Group group2 = 저장한다(new Group(GROUP_1.interest, "그룹11", GROUP_1.description, GROUP_1.대표_사진()));
        저장한다(GROUP_2.생성());

        final List<Long> expected = List.of(group1.getId(), group2.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = groupDao.findIdsByNameLike("1", page);
        final List<Long> actual = ids.getIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 그룹_관심사로_목록을_조회한다() {
        // given
        final String interest = "관심사1";
        final Group group1 = 저장한다(new Group(interest, GROUP_1.name, GROUP_1.description, GROUP_1.대표_사진()));
        final Group group2 = 저장한다(new Group(interest, GROUP_2.name, GROUP_2.description, GROUP_2.대표_사진()));
        저장한다(new Group("관심사2", GROUP_3.name, GROUP_3.description, GROUP_3.대표_사진()));

        final List<Long> expected = List.of(group1.getId(), group2.getId());
        final NoOffsetIdPage page = new NoOffsetIdPage(null, 5);

        // when
        final LongIdList ids = groupDao.findIdsByInterest(interest, page);
        final List<Long> actual = ids.getIds();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
