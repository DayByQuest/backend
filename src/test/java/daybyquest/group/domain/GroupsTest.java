package daybyquest.group.domain;


import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistGroupException;
import daybyquest.interest.domain.Interests;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GroupsTest {

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private GroupUsers groupUsers;

    @Mock
    private Interests interests;

    @InjectMocks
    private Groups groups;

    @Test
    void 사용자와_관심사를_검증하고_그룹을_저장한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        given(groupRepository.save(any(Group.class))).willReturn(GROUP_1.생성(groupId));

        // when
        final Long actualId = groups.save(userId, GROUP_1.생성()).getId();

        // then
        assertAll(() -> {
            then(interests).should().validateInterest(GROUP_1.interest);
            then(groupRepository).should().save(any(Group.class));
            assertThat(actualId).isEqualTo(groupId);
        });
    }

    @Test
    void 그룹을_저장할_때_중복_이름이_있다면_에외를_던진다() {
        // given
        final Long userId = 1L;
        given(groupRepository.existsByName(GROUP_1.name)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> groups.save(userId, GROUP_1.생성()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 그룹_이름_유일성을_검증한다() {
        // given & when
        groups.validateNotExistentByName(GROUP_1.name);

        // then
        then(groupRepository).should().existsByName(GROUP_1.name);
    }

    @Test
    void 그룹_이름_유일성을_검증_시_이미_있다면_예외를_던진다() {
        // given
        given(groupRepository.existsByName(GROUP_1.name)).willReturn(true);

        // when
        assertThatThrownBy(() -> groups.validateNotExistentByName(GROUP_1.name))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 그룹을_저장할_땐_사용자를_함께_저장한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        given(groupRepository.save(any(Group.class))).willReturn(GROUP_1.생성(groupId));

        // when
        groups.save(userId, GROUP_1.생성());

        // then
        then(groupUsers).should().addUser(any(GroupUser.class));
    }

    @Test
    void 그룹_ID_존재_여부를_검증한다() {
        // given
        final Long groupId = 1L;
        given(groupRepository.existsById(groupId)).willReturn(true);

        // when
        groups.validateExistentById(groupId);

        // then
        then(groupRepository).should().existsById(groupId);
    }

    @Test
    void 게시물_ID_존재_여부를_검증_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> groups.validateExistentById(1L))
                .isInstanceOf(NotExistGroupException.class);
    }

    @Test
    void ID로_그룹을_조회한다() {
        // given
        final Long groupId = 1L;
        final Group expected = GROUP_1.생성(groupId);
        given(groupRepository.findById(groupId)).willReturn(Optional.of(expected));

        // when
        final Group actual = groups.getById(groupId);

        // then
        assertAll(() -> {
            then(groupRepository).should().findById(groupId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        });
    }

    @Test
    void ID로_조회_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> groups.getById(1L))
                .isInstanceOf(NotExistGroupException.class);
    }
}
