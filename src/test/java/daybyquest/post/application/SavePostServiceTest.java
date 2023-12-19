package daybyquest.post.application;

import static daybyquest.global.error.ExceptionCode.NOT_ACCEPTED_QUEST;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import daybyquest.post.domain.Post;
import daybyquest.post.dto.request.SavePostRequest;
import daybyquest.support.fixture.BadgeFixtures;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SavePostServiceTest extends ServiceTest {

    @Autowired
    private SavePostService savePostService;

    @Test
    void 게시물을_저장한다() {
        // given
        final Long aliceId = ALICE를_저장한다();

        final SavePostRequest request = 게시물_생성_요청(POST_1.생성(aliceId));
        final List<MultipartFile> files = 사진_파일(POST_1.imageIdentifiers);

        // when
        final Long postId = savePostService.invoke(aliceId, request, files);

        // then
        final Post post = posts.getById(postId);
        final List<String> actualFiles = post.getImages().stream().map(Image::getIdentifier).toList();
        assertAll(() -> {
            assertThat(post.getId()).isEqualTo(postId);
            assertThat(post.getUserId()).isEqualTo(aliceId);
            assertThat(post.getContent()).isEqualTo(POST_1.content);
            assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(POST_1.imageIdentifiers);
        });
    }

    @Test
    void 퀘스트를_링크할_수_있다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();
        participants.saveWithUserIdAndQuestId(aliceId, questId);

        final SavePostRequest request = 게시물_생성_요청(POST_1.생성(aliceId, questId));
        final List<MultipartFile> files = 사진_파일(POST_1.imageIdentifiers);

        // when
        final Long postId = savePostService.invoke(aliceId, request, files);

        // then
        final Post post = posts.getById(postId);
        assertAll(() -> {
            assertThat(post.getQuestId()).isEqualTo(questId);
            then(postClient).should().requestJudge(eq(postId), any(), any());
        });
    }

    @Test
    void 수행_중이지_않은_퀘스트는_링크할_수_없다() {
        // given
        final Long aliceId = 중재자_권한으로_ALICE를_저장한다();

        final Long badgeId = badges.save(BadgeFixtures.BADGE_1.생성()).getId();
        final Long questId = quests.save(QUEST_1.세부사항을_설정한다(QUEST_1.일반_퀘스트_생성(badgeId))).getId();

        final SavePostRequest request = 게시물_생성_요청(POST_1.생성(aliceId, questId));
        final List<MultipartFile> files = 사진_파일(POST_1.imageIdentifiers);

        // when
        assertThatThrownBy(() -> savePostService.invoke(aliceId, request, files))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(NOT_ACCEPTED_QUEST.getMessage());
    }

    private SavePostRequest 게시물_생성_요청(final Post post) {
        final SavePostRequest request = new SavePostRequest();
        ReflectionTestUtils.setField(request, "content", post.getContent());
        if (post.isQuestLinked()) {
            ReflectionTestUtils.setField(request, "questId", post.getQuestId());
        }
        return request;
    }
}
