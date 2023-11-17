package daybyquest.group.domain;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupUserException;
import daybyquest.user.domain.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GroupUsersTest {

    @Mock
    private GroupUserRepository groupUserRepository;

    @Mock
    private Users users;

    @Mock
    private Groups groups;

    @InjectMocks
    private GroupUsers groupUsers;

    @Test
    void 사용자를_추가할_땐_그룹ID의_존재여부와_이미_회원이_아닌지_검증한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(userId, group);

        // when
        groupUsers.addUser(groupUser);

        // then
        assertAll(() -> {
            then(groups).should().validateExistentById(groupId);
            then(groupUserRepository).should().existsByUserIdAndGroupId(userId, groupId);
            then(groupUserRepository).should().save(any(GroupUser.class));
        });
    }

    @Test
    void 사용자를_추가할_때_회원_역할이라면_사용자ID_존재여부를_검증한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(userId, group);

        // when
        groupUsers.addUser(groupUser);

        // then
        then(users).should().validateExistentById(userId);
    }

    @Test
    void 사용자를_추가할_때_관리자_역할이라면_MODERATOR_여부를_검증한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupManager(userId, group);

        // when
        groupUsers.addUser(groupUser);

        // then
        then(users).should().validateModeratorById(userId);
    }

    @Test
    void 사용자를_추가할_때_이미_가입한_그룹이라면_예외를_던진다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(userId, group);
        given(groupUserRepository.existsByUserIdAndGroupId(userId, groupId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> groupUsers.addUser(groupUser))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자_ID와_그룹_ID로_조회한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser expected = GroupUser.createGroupMember(userId, group);
        given(groupUserRepository.findByUserIdAndGroupId(userId, groupId)).willReturn(Optional.of(expected));

        // when
        final GroupUser actual = groupUsers.getByUserIdAndGroupId(userId, groupId);

        // then
        assertAll(() -> {
            then(groupUserRepository).should().findByUserIdAndGroupId(userId, groupId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        });
    }

    @Test
    void 사용자_ID와_그룹_ID로_조회_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> groupUsers.getByUserIdAndGroupId(1L, 2L))
                .isInstanceOf(NotExistGroupUserException.class);
    }

    @Test
    void 그룹을_탈퇴한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupMember(userId, group);

        // when
        groupUsers.delete(groupUser);

        // then
        then(groupUserRepository).should().delete(groupUser);
    }

    @Test
    void 그룹_관리자는_탈퇴할_수_없다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Group group = GROUP_1.생성(groupId);
        final GroupUser groupUser = GroupUser.createGroupManager(userId, group);

        // when
        assertThatThrownBy(() -> groupUsers.delete(groupUser))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 사용자_ID와_그룹_ID로_그룹에_속했는지를_검증한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        given(groupUserRepository.existsByUserIdAndGroupId(userId, groupId)).willReturn(true);

        // when
        groupUsers.validateExistentByUserIdAndGroupId(userId, groupId);

        // then
        then(groupUserRepository).should().existsByUserIdAndGroupId(userId, groupId);
    }

    @Test
    void 사용자_ID_와_그룹_ID로_그룹_가입_검증_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> groupUsers.validateExistentByUserIdAndGroupId(1L, 2L))
                .isInstanceOf(NotExistGroupUserException.class);
    }
}
