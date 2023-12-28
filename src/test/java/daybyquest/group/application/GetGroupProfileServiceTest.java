package daybyquest.group.application;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.dto.response.GroupResponse;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetGroupProfileServiceTest extends ServiceTest {

    @Autowired
    private GetGroupProfileService getGroupProfileService;

    @Test
    void 그룹_프로필을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        final Long groupId = groups.save(aliceId, GROUP_1.생성()).getId();

        // when
        final GroupResponse response = getGroupProfileService.invoke(aliceId, groupId);

        // then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(groupId);
            assertThat(response.name()).isEqualTo(GROUP_1.name);
            assertThat(response.description()).isEqualTo(GROUP_1.description);
            assertThat(response.interest()).isEqualTo(GROUP_1.interest);
            assertThat(response.imageIdentifier()).isEqualTo(GROUP_1.imageIdentifier);
            assertThat(response.userCount()).isEqualTo(1);
        });
    }

    @Test
    void 그룹원_수가_포함된다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        final Long charlieId = CHARLIE를_저장한다();
        interests.save(INTEREST.생성());
        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Long groupId = group.getId();
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));
        groupUsers.addUser(GroupUser.createGroupMember(charlieId, group));

        // when
        final GroupResponse response = getGroupProfileService.invoke(aliceId, groupId);

        // then
        assertThat(response.userCount()).isEqualTo(3);
    }

    @Test
    void 그룹_회원_여부가_포함된다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        final Long charlieId = CHARLIE를_저장한다();
        interests.save(INTEREST.생성());
        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Long groupId = group.getId();
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        // when
        final GroupResponse aliceResponse = getGroupProfileService.invoke(aliceId, groupId);
        final GroupResponse bobResponse = getGroupProfileService.invoke(bobId, groupId);
        final GroupResponse charlieResponse = getGroupProfileService.invoke(charlieId, groupId);

        // then
        assertAll(() -> {
            assertThat(aliceResponse.isGroupManager()).isTrue();
            assertThat(aliceResponse.isGroupMember()).isTrue();
            assertThat(bobResponse.isGroupManager()).isFalse();
            assertThat(bobResponse.isGroupMember()).isTrue();
            assertThat(charlieResponse.isGroupManager()).isFalse();
            assertThat(charlieResponse.isGroupMember()).isFalse();
        });
    }
}
