package daybyquest.post.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_JUDGED_POST;
import static daybyquest.global.error.ExceptionCode.NOT_LINKED_POST;
import static daybyquest.post.domain.PostState.FAIL;
import static daybyquest.post.domain.PostState.NEED_CHECK;
import static daybyquest.post.domain.PostState.SUCCESS;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.InterestFixtures.INTEREST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.group.domain.Group;
import daybyquest.post.domain.Post;
import daybyquest.post.dto.request.JudgePostRequest;
import daybyquest.quest.domain.Quest;
import daybyquest.support.fixture.BadgeFixtures;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

public class JudgePostServiceTest extends ServiceTest {

    @Autowired
    private JudgePostService judgePostService;

    @Test
    void 일반_퀘스트가_링크된_게시물을_성공_판정한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Long postId = posts.save(POST_1.생성(aliceId, questId)).getId();

        final JudgePostRequest request = 게시물_판정_요청("SUCCESS");

        // when
        judgePostService.invoke(postId, request);

        // then
        final Post post = posts.getById(postId);
        assertThat(post.getState()).isEqualTo(SUCCESS);
    }

    @Test
    void 그룹_퀘스트가_링크된_게시물을_성공_판정한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Long postId = posts.save(POST_1.생성(aliceId, questId)).getId();

        final JudgePostRequest request = 게시물_판정_요청("SUCCESS");

        // when
        judgePostService.invoke(postId, request);

        // then
        final Post post = posts.getById(postId);
        assertThat(post.getState()).isEqualTo(SUCCESS);
    }

    @Test
    void 일반_퀘스트가_링크된_게시물을_실패_판정한다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Long postId = posts.save(POST_1.생성(aliceId, questId)).getId();

        final JudgePostRequest request = 게시물_판정_요청("FAIL");

        // when
        judgePostService.invoke(postId, request);

        // then
        final Post post = posts.getById(postId);
        assertThat(post.getState()).isEqualTo(FAIL);
    }

    @Test
    void 그룹_퀘스트가_링크된_게시물을_실패_판정하면_확인_필요_상태가_된다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();
        interests.save(INTEREST.생성());

        final Group group = groups.save(aliceId, GROUP_1.생성());
        final Quest quest = quests.save(QUEST_1.보상_없이_세부사항을_설정한다(QUEST_1.그룹_퀘스트_생성(group)));
        final Long questId = quest.getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Long postId = posts.save(POST_1.생성(aliceId, questId)).getId();

        final JudgePostRequest request = 게시물_판정_요청("FAIL");

        // when
        judgePostService.invoke(postId, request);

        // then
        final Post post = posts.getById(postId);
        assertThat(post.getState()).isEqualTo(NEED_CHECK);
    }

    @Test
    void 퀘스트가_링크되지_않은_게시물은_판정할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long postId = posts.save(POST_1.생성(aliceId)).getId();

        final JudgePostRequest request = 게시물_판정_요청("FAIL");

        // when
        assertThatThrownBy(() -> judgePostService.invoke(postId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_LINKED_POST.getMessage());
    }

    @Test
    void 이미_판정된_게시물은_판정할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final Post post = POST_1.생성(aliceId, questId);
        post.success();
        final Long postId = posts.save(post).getId();

        final JudgePostRequest request = 게시물_판정_요청("FAIL");

        // when
        assertThatThrownBy(() -> judgePostService.invoke(postId, request))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_JUDGED_POST.getMessage());
    }

    private JudgePostRequest 게시물_판정_요청(final String judgement) {
        final JudgePostRequest request = new JudgePostRequest();
        ReflectionTestUtils.setField(request, "judgement", judgement);
        return request;
    }
}
