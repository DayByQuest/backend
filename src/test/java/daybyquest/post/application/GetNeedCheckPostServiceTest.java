package daybyquest.post.application;

import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.Group;
import daybyquest.group.domain.GroupUser;
import daybyquest.post.domain.Post;
import daybyquest.post.dto.response.SimplePostResponse;
import daybyquest.quest.domain.Quest;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetNeedCheckPostServiceTest extends ServiceTest {

    @Autowired
    private GetNeedCheckPostsService getNeedCheckPostsService;

    @Test
    void 확인이_필요한_게시물을_조회한다() {
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

        final Post post1 = POST_1.생성(aliceId, questId);
        post1.needCheck();
        final Post post2 = POST_1.생성(aliceId, questId);
        post2.needCheck();

        final List<Long> actual = List.of(posts.save(post1).getId(),
                posts.save(post2).getId());
        posts.save(POST_1.생성(bobId));

        // when
        final List<Long> expected = getNeedCheckPostsService.invoke(aliceId, group.getId()).posts()
                .stream().map(SimplePostResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void 관리자가_아니라면_조회할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();

        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        // when & then
        assertThatThrownBy(() -> getNeedCheckPostsService.invoke(bobId, questId))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_GROUP_MANAGER.getMessage());
    }
}
