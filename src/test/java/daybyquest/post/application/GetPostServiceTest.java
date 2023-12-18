package daybyquest.post.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.badge.domain.Badges;
import daybyquest.participant.domain.Participants;
import daybyquest.post.domain.Posts;
import daybyquest.post.dto.response.PostWithQuestResponse;
import daybyquest.post.dto.response.PostWithQuestResponse.SimpleQuestResponse;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.domain.Quests;
import daybyquest.support.test.ServiceTest;
import daybyquest.user.dto.response.ProfileResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetPostServiceTest extends ServiceTest {

    @Autowired
    private GetPostService getPostService;

    @Autowired
    private Posts posts;

    @Autowired
    private Quests quests;

    @Autowired
    private Badges badges;

    @Autowired
    private Participants participants;

    @Test
    void 게시물을_조회한다() {
        // given
        final Long id = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(id)).getId();

        // when
        final PostWithQuestResponse response = getPostService.invoke(id, postId);

        // then
        assertAll(() -> {
            assertThat(response.id()).isEqualTo(postId);
            assertThat(response.content()).isEqualTo(POST_1.content);
            assertThat(response.imageIdentifiers())
                    .containsExactlyInAnyOrderElementsOf(POST_1.imageIdentifiers);
        });
    }

    @Test
    void 회원_정보가_함께_조회된다() {
        // given
        final Long id = ALICE를_저장한다();
        final Long postId = posts.save(POST_1.생성(id)).getId();

        // when
        final ProfileResponse response = getPostService.invoke(id, postId).author();

        // then
        assertAll(() -> {
            assertThat(response.username()).isEqualTo(ALICE.username);
            assertThat(response.name()).isEqualTo(ALICE.name);
            assertThat(response.imageIdentifier()).isEqualTo(ALICE.imageIdentifier);
        });
    }

    @Test
    void 퀘스트_정보가_함께_조회된다() {
        // given
        final Long id = ALICE를_저장한다();
        final Long badgeId = badges.save(BADGE_1.생성());
        final Quest quest = QUEST_1.일반_퀘스트_생성(badgeId);
        QUEST_1.세부사항을_설정한다(quest);
        final Long questId = quests.save(quest).getId();
        participants.saveWithUserIdAndQuestId(id, questId);
        final Long postId = posts.save(POST_1.생성(id, questId)).getId();

        // when
        final SimpleQuestResponse response = getPostService.invoke(id, postId).quest();

        // then
        assertAll(() -> {
            assertThat(response.questId()).isEqualTo(questId);
            assertThat(response.title()).isEqualTo(QUEST_1.title);
        });
    }
}
