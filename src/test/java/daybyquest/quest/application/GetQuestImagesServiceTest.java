package daybyquest.quest.application;

import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.quest.domain.Quest;
import daybyquest.quest.dto.response.QuestImagesResponse;
import daybyquest.support.test.ServiceTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GetQuestImagesServiceTest extends ServiceTest {

    @Autowired
    private GetQuestImagesService getQuestImagesService;

    @Test
    void 퀘스트_예시_사진을_조회한다() {
        // given
        final Long badgeId = badges.save(BADGE_1.생성()).getId();

        final Quest expected = QUEST_1.일반_퀘스트_생성(badgeId);
        QUEST_1.세부사항을_설정한다(expected);
        final Long questId = quests.save(expected).getId();

        // when
        final QuestImagesResponse response = getQuestImagesService.invoke(questId);

        // then
        assertAll(() -> {
            assertThat(response.description()).isEqualTo(QUEST_1.imageDescription);
            assertThat(response.imageIdentifiers())
                    .containsExactlyInAnyOrderElementsOf(QUEST_1.imageIdentifiers);
        });
    }
}
