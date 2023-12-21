package daybyquest.quest.application;

import static daybyquest.global.error.ExceptionCode.ALREADY_EXIST_REWARD;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistBadgeException;
import daybyquest.image.domain.Image;
import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.request.SaveQuestRequest;
import daybyquest.support.test.ServiceTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveQuestServiceTest extends ServiceTest {

    @Autowired
    private SaveQuestService saveQuestService;

    @Test
    void 퀘스트를_저장한다() {
        // given
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        final SaveQuestRequest request = 퀘스트_생성_요청(QUEST_1.일반_퀘스트_생성(badgeId));
        final List<MultipartFile> files = 사진_파일(QUEST_1.imageIdentifiers);

        // when
        final Long questId = saveQuestService.invoke(request, files);

        // then
        final Quest quest = quests.getById(questId);
        final List<String> actualFiles = quest.getImages().stream().map(Image::getIdentifier).toList();
        assertAll(() -> {
            assertThat(quest.getId()).isEqualTo(questId);
            assertThat(quest.getImageDescription()).isEqualTo(QUEST_1.imageDescription);
            assertThat(quest.getBadgeId()).isEqualTo(badgeId);
            assertThat(quest.getImage().getIdentifier()).isEqualTo(BADGE_1.imageIdentifier);
            assertThat(actualFiles).containsExactlyInAnyOrderElementsOf(QUEST_1.imageIdentifiers);
            then(questClient).should().requestLabels(eq(questId), any(), any());
        });
    }

    @Test
    void 존재하지_않는_뱃지를_보상으로_지정할_수_없다() {
        // given
        final SaveQuestRequest request = 퀘스트_생성_요청(QUEST_1.일반_퀘스트_생성(1L));
        final List<MultipartFile> files = 사진_파일(QUEST_1.imageIdentifiers);

        // when & then
        assertThatThrownBy(() -> saveQuestService.invoke(request, files))
                .isInstanceOf(NotExistBadgeException.class);
    }

    @Test
    void 이미_보상으로_지정된_뱃지를_보상으로_지정할_수_없다() {
        // given
        final Long badgeId = badges.save(BADGE_1.생성()).getId();
        quests.save(QUEST_2.일반_퀘스트_생성(badgeId));

        final SaveQuestRequest request = 퀘스트_생성_요청(QUEST_1.일반_퀘스트_생성(badgeId));
        final List<MultipartFile> files = 사진_파일(QUEST_1.imageIdentifiers);

        // when & then
        assertThatThrownBy(() -> saveQuestService.invoke(request, files))
                .isInstanceOf(InvalidDomainException.class)
                .hasMessageContaining(ALREADY_EXIST_REWARD.getMessage());
    }

    private SaveQuestRequest 퀘스트_생성_요청(final Quest quest) {
        final SaveQuestRequest request = new SaveQuestRequest();
        ReflectionTestUtils.setField(request, "badgeId", quest.getBadgeId());
        ReflectionTestUtils.setField(request, "imageDescription", quest.getImageDescription());
        return request;
    }
}
