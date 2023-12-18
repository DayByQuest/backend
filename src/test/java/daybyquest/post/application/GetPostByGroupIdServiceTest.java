package daybyquest.post.application;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.post.dto.response.PostResponse;
import daybyquest.quest.domain.Quest;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPostByGroupIdServiceTest extends ServiceTest {

    @Autowired
    private GetPostByGroupIdService getPostByGroupIdService;

    @Test
    void 그룹으로_게시물을_조회한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();

        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        participants.saveWithUserIdAndQuestId(aliceId, questId);
        participants.saveWithUserIdAndQuestId(bobId, questId);

        final List<Long> actual = List.of(posts.save(POST_1.생성(aliceId, questId)).getId(),
                posts.save(POST_1.생성(aliceId, questId)).getId(),
                posts.save(POST_1.생성(bobId, questId)).getId());
        posts.save(POST_1.생성(bobId));

        // when
        final List<Long> expected = getPostByGroupIdService.invoke(aliceId, group.getId(), 페이지()).posts()
                .stream().map(PostResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
