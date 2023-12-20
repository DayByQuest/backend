package daybyquest.quest.application.group;

import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.SaveGroupQuestDetailRequest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class SaveGroupQuestDetailServiceTest extends ServiceTest {

    @Autowired
    private SaveGroupQuestDetailService saveGroupQuestDetailService;

    @Test
    void 퀘스트_세부사항을_설정한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        final Long groupId = groups.save(aliceId, GROUP_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.그룹_퀘스트_생성(groupId)).getId();

        final Quest quest = QUEST_1.그룹_퀘스트_생성(questId, groupId);
        QUEST_1.보상_없이_세부사항을_설정한다(quest);
        final SaveGroupQuestDetailRequest request = 퀘스트_세부사항_설정_요청(quest);

        // when
        saveGroupQuestDetailService.invoke(aliceId, questId, request);

        // then
        final Quest actual = quests.getById(questId);
        assertAll(() -> {
            assertThat(actual.getId()).isEqualTo(questId);
            assertThat(actual.getTitle()).isEqualTo(QUEST_1.title);
            assertThat(actual.getContent()).isEqualTo(QUEST_1.content);
            assertThat(actual.getInterestName()).isEqualTo(QUEST_1.interest);
            assertThat(actual.getLabel()).isEqualTo(QUEST_1.label);
        });
    }

    @Test
    void 그룹관리자만_세부사항을_설정할_수_있다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        interests.save(INTEREST.생성());
        final Group group = groups.save(aliceId, GROUP_1.생성());
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));
        final Long questId = quests.save(QUEST_1.그룹_퀘스트_생성(group.getId())).getId();

        final Quest quest = QUEST_1.그룹_퀘스트_생성(questId, group.getId());
        QUEST_1.보상_없이_세부사항을_설정한다(quest);
        final SaveGroupQuestDetailRequest request = 퀘스트_세부사항_설정_요청(quest);

        // when
        assertThatThrownBy(() -> saveGroupQuestDetailService.invoke(bobId, questId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_GROUP_MANAGER.getMessage());
    }

    private SaveGroupQuestDetailRequest 퀘스트_세부사항_설정_요청(final Quest quest) {
        final SaveGroupQuestDetailRequest request = new SaveGroupQuestDetailRequest();
        ReflectionTestUtils.setField(request, "title", quest.getTitle());
        ReflectionTestUtils.setField(request, "content", quest.getContent());
        ReflectionTestUtils.setField(request, "interest", quest.getInterestName());
        ReflectionTestUtils.setField(request, "label", quest.getLabel());
        ReflectionTestUtils.setField(request, "expiredAt", quest.getExpiredAt());
        return request;
    }
}
