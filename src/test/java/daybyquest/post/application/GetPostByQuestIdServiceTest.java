package daybyquest.post.application;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.post.dto.response.PostResponse;
import daybyquest.support.fixture.BadgeFixtures;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPostByQuestIdServiceTest extends ServiceTest {

    @Autowired
    private GetPostByQuestIdService getPostByQuestIdService;

    @Test
    void 퀘스트로_게시물을_조회한다() {
        // given
        final Long aliceId = ALICE를_저장한다();
        final Long bobId = BOB을_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성());
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();

        participants.saveWithUserIdAndQuestId(aliceId, questId);
        participants.saveWithUserIdAndQuestId(bobId, questId);

        final List<Long> actual = List.of(posts.save(POST_1.생성(aliceId, questId)).getId(),
                posts.save(POST_1.생성(aliceId, questId)).getId(),
                posts.save(POST_1.생성(bobId, questId)).getId());
        posts.save(POST_1.생성(bobId));

        // when
        final List<Long> expected = getPostByQuestIdService.invoke(aliceId, questId, 페이지()).posts()
                .stream().map(PostResponse::id).toList();

        // then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
