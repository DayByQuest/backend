package daybyquest.post.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_JUDGED_POST;
import static daybyquest.global.error.ExceptionCode.NOT_GROUP_MANAGER;
import static daybyquest.global.error.ExceptionCode.NOT_LINKED_POST;
import static daybyquest.post.domain.PostState.FAIL;
import static daybyquest.post.domain.PostState.SUCCESS;
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
import daybyquest.post.dto.request.CheckPostRequest;
import daybyquest.quest.domain.Quest;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class CheckPostServiceTest extends ServiceTest {

    @Autowired
    private CheckPostService checkPostService;

    @Test
    void 그룹장이_그룹_퀘스트를_성공_시킨다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Post post = POST_1.생성(aliceId, questId);
        post.needCheck();
        final Long postId = posts.save(post).getId();

        final CheckPostRequest request = 게시물_판정_요청(true);

        // when
        checkPostService.invoke(aliceId, postId, request);

        // then
        final Post actual = posts.getById(postId);
        assertThat(actual.getState()).isEqualTo(SUCCESS);
    }

    @Test
    void 그룹장이_그룹_퀘스트를_실패_시킨다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Post post = POST_1.생성(aliceId, questId);
        post.needCheck();
        final Long postId = posts.save(post).getId();

        final CheckPostRequest request = 게시물_판정_요청(false);

        // when
        checkPostService.invoke(aliceId, postId, request);

        // then
        final Post actual = posts.getById(postId);
        assertThat(actual.getState()).isEqualTo(FAIL);
    }

    @Test
    void 그룹장이_아니면_예외를_던진다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        final Long bobId = 중재자_권한으로_BOB을_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        groupUsers.addUser(GroupUser.createGroupMember(bobId, group));

        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Post post = POST_1.생성(aliceId, questId);
        post.needCheck();
        final Long postId = posts.save(post).getId();

        final CheckPostRequest request = 게시물_판정_요청(false);

        // when & then
        assertThatThrownBy(() -> checkPostService.invoke(bobId, postId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_GROUP_MANAGER.getMessage());
    }

    @Test
    void 퀘스트가_링크되지_않은_게시물을_확인할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long postId = posts.save(POST_1.생성(aliceId)).getId();

        final CheckPostRequest request = 게시물_판정_요청(true);

        // when & then
        assertThatThrownBy(() -> checkPostService.invoke(aliceId, postId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_LINKED_POST.getMessage());
    }

    @Test
    void 이미_확인된_게시물은_확인할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Post post = POST_1.생성(aliceId, questId);
        post.success();
        final Long postId = posts.save(post).getId();

        final CheckPostRequest request = 게시물_판정_요청(true);

        // when & then
        assertThatThrownBy(() -> checkPostService.invoke(aliceId, postId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_JUDGED_POST.getMessage());
    }

    private CheckPostRequest 게시물_판정_요청(final boolean approval) {
        final CheckPostRequest request = new CheckPostRequest();
        ReflectionTestUtils.setField(request, "approval", approval);
        return request;
    }
}
