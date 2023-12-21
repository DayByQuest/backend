package daybyquest.quest.application.group;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_2;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.group.domain.Group;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetGroupQuestsServiceTest extends ServiceTest {

    @Autowired
    private GetGroupQuestsService getGroupQuestsService;

    @Test
    void 그룹_퀘스트_목록을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());
        final Group group1 = groups.save(aliceId, GROUP_1.생성());
        final Group group2 = groups.save(aliceId, GROUP_2.생성());

        final List<Long> expected = List.of(
                quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group1))).getId(),
                quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group1))).getId());
        quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group2)));

        // when
        final MultipleQuestsResponse response = getGroupQuestsService.invoke(aliceId, group1.getId());
        final List<Long> actual = response.quests().stream().map(QuestResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
