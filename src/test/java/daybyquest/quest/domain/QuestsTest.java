package daybyquest.quest.domain;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.badge.domain.Badges;
import daybyquest.global.error.exception.NotExistQuestException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class QuestsTest {

    @Mock
    private QuestRepository questRepository;

    @Mock
    private Badges badges;

    @InjectMocks
    private Quests quests;

    @Test
    void 뱃지_ID를_검증하고_게시물을_저장한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        given(questRepository.save(any(Quest.class))).willReturn(QUEST_1.일반_퀘스트_생성(questId, badgeId));

        // when
        quests.save(QUEST_1.일반_퀘스트_생성(badgeId));

        // then
        assertAll(() -> {
            then(badges).should().validateExistentById(badgeId);
            then(questRepository).should().save(any(Quest.class));
        });
    }

    @Test
    void 뱃지_ID가_없으면_그냥_게시물을_저장한다() {
        // given
        final Long questId = 1L;
        given(questRepository.save(any(Quest.class))).willReturn(QUEST_1.일반_퀘스트_생성(questId, null));

        // when
        quests.save(QUEST_1.일반_퀘스트_생성());

        // then
        then(questRepository).should().save(any(Quest.class));
    }

    @Test
    void ID를_통해_퀘스트를_조회한다() {
        // given
        final Long questId = 1L;
        final Long badgeId = 2L;
        final Quest expected = QUEST_1.일반_퀘스트_생성(questId, badgeId);
        given(questRepository.findById(questId)).willReturn(Optional.of(expected));

        // when
        final Quest actual = quests.getById(questId);

        // then
        assertAll(() -> {
            then(questRepository).should().findById(questId);
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        });
    }

    @Test
    void ID를_통한_조회_시_퀘스트가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> quests.getById(1L))
                .isInstanceOf(NotExistQuestException.class);
    }
}
