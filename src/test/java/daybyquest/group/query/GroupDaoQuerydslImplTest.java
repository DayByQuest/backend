package daybyquest.group.query;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(GroupDaoQuerydslImpl.class)
public class GroupDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private GroupDaoQuerydslImpl groupDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void 그룹_ID로_데이터를_조회한다() {
        // given
        final User user = entityManager.merge(BOB.생성());
        final Group group = entityManager.merge(GROUP_1.생성());

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
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final User charlie = entityManager.merge(CHARLIE.생성());
        final Group group = entityManager.merge(GROUP_1.생성());

        entityManager.merge(GroupUser.createGroupMember(bob.getId(), group));
        entityManager.merge(GroupUser.createGroupMember(alice.getId(), group));
        entityManager.merge(GroupUser.createGroupManager(charlie.getId(), group));

        // when
        final GroupData groupData = groupDao.getById(bob.getId(), group.getId());

        // then
        assertThat(groupData.getUserCount()).isEqualTo(3);
    }

    @Test
    void 그룹_관리자_여부가_함께_조회된다() {
        // given
        final User bob = entityManager.merge(BOB.생성());
        final User alice = entityManager.merge(ALICE.생성());
        final Group group = entityManager.merge(GROUP_1.생성());

        entityManager.merge(GroupUser.createGroupMember(bob.getId(), group));
        entityManager.merge(GroupUser.createGroupManager(alice.getId(), group));

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
}
