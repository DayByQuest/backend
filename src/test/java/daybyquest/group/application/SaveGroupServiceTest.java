package daybyquest.group.application;

import static daybyquest.global.error.ExceptionCode.DUPLICATED_GROUP_NAME;
import static daybyquest.group.domain.GroupUserRole.MANAGER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.BadAuthorizationException;
import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.group.dto.request.SaveGroupRequest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveGroupServiceTest extends ServiceTest {

    @Autowired
    private SaveGroupService saveGroupService;

    @Test
    void 그룹을_생성한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final SaveGroupRequest request = 그룹_생성_요청(GROUP_1.생성());
        final MultipartFile file = 사진_파일(GROUP_1.imageIdentifier);

        // when
        final Long groupId = saveGroupService.invoke(aliceId, request, file);
        final Group group = groups.getById(groupId);

        // then
        assertAll(() -> {
            assertThat(group.getId()).isEqualTo(groupId);
            assertThat(group.getName()).isEqualTo(GROUP_1.name);
            assertThat(group.getDescription()).isEqualTo(GROUP_1.description);
            assertThat(group.getInterest()).isEqualTo(GROUP_1.interest);
            assertThat(group.getImage().getIdentifier()).isEqualTo(GROUP_1.imageIdentifier);
        });
    }

    @Test
    void 그룹을_만들면_생성된_그룹의_관리자가_된다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final SaveGroupRequest request = 그룹_생성_요청(GROUP_1.생성());
        final MultipartFile file = 사진_파일(GROUP_1.imageIdentifier);

        // when
        final Long groupId = saveGroupService.invoke(aliceId, request, file);
        final GroupUser groupUser = groupUsers.getByUserIdAndGroupId(aliceId, groupId);

        // then
        assertThat(groupUser.getRole()).isEqualTo(MANAGER);
    }

    @Test
    void 일반_사용자는_그룹을_생성할_수_없다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final SaveGroupRequest request = 그룹_생성_요청(GROUP_1.생성());
        final MultipartFile file = 사진_파일(GROUP_1.imageIdentifier);

        // when & then
        assertThatThrownBy(() -> saveGroupService.invoke(aliceId, request, file))
                .isInstanceOf(BadAuthorizationException.class);
    }

    @Test
    void 그룹_이름은_중복될_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        groups.save(aliceId, GROUP_1.생성());

        final SaveGroupRequest request = 그룹_생성_요청(GROUP_1.생성());
        final MultipartFile file = 사진_파일(GROUP_1.imageIdentifier);

        // when & then
        assertThatThrownBy(() -> saveGroupService.invoke(aliceId, request, file))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(DUPLICATED_GROUP_NAME.getMessage());
    }

    private SaveGroupRequest 그룹_생성_요청(final Group group) {
        final SaveGroupRequest request = new SaveGroupRequest();
        ReflectionTestUtils.setField(request, "name", group.getName());
        ReflectionTestUtils.setField(request, "description", group.getDescription());
        ReflectionTestUtils.setField(request, "interest", group.getInterest());
        return request;
    }
}
