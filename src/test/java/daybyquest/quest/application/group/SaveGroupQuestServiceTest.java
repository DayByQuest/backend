package daybyquest.quest.application.group;

import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.image.domain.Image;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.SaveGroupQuestRequest;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveGroupQuestServiceTest extends ServiceTest {

    @Autowired
    private SaveGroupQuestService saveGroupQuestService;

    @Test
    void 퀘스트를_저장한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        final Long groupId = groups.save(aliceId, GROUP_1.생성()).getId();

        final SaveGroupQuestRequest request = 퀘스트_생성_요청(QUEST_1.그룹_퀘스트_생성(groupId));
        final List<MultipartFile> files = 사진_파일(QUEST_1.imageIdentifiers);

        // when
        final Long questId = saveGroupQuestService.invoke(aliceId, request, files);

        // then
        final Quest quest = quests.getById(questId);
        final List<String> actualFiles = quest.getImages().stream().map(Image::getIdentifier).toList();
        assertAll(() -> {
            assertThat(quest.getId()).isEqualTo(questId);
            assertThat(quest.getImageDescription()).isEqualTo(QUEST_1.imageDescription);
            assertThat(quest.getGroupId()).isEqualTo(groupId);
            assertThat(quest.getImage().getIdentifier()).isEqualTo(GROUP_1.imageIdentifier);
            assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(QUEST_1.imageIdentifiers);
            then(questClient).should().requestLabels(eq(questId), any(), any());
        });
    }

    @Test
    void 그룹관리자만_그룹_퀘스트를_생성할_수_있다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        interests.save(INTEREST.생성());
        final Group group = groups.save(aliceId, GROUP_1.생성());
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        final SaveGroupQuestRequest request = 퀘스트_생성_요청(QUEST_1.그룹_퀘스트_생성(group));
        final List<MultipartFile> files = 사진_파일(QUEST_1.imageIdentifiers);

        // when & then
        assertThatThrownBy(() -> saveGroupQuestService.invoke(bobId, request, files))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_GROUP_MANAGER.getMessage());
    }

    private SaveGroupQuestRequest 퀘스트_생성_요청(final Quest quest) {
        final SaveGroupQuestRequest request = new SaveGroupQuestRequest();
        ReflectionTestUtils.setField(request, "groupId", quest.getGroupId());
        ReflectionTestUtils.setField(request, "imageDescription", quest.getImageDescription());
        return request;
    }
}
