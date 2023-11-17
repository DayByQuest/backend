package daybyquest.quest.domain;

import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.badge.domain.Badges;
import daybyquest.global.error.exception.InvalidDomainException;
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
            then(questRepository).should().existsByBadgeId(badgeId);
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
    void 동일한_뱃지가_보상인_퀘스트가_이미_있다면_예외를_던진다() {
        // given
        final Long badgeId = 1L;
        given(questRepository.existsByBadgeId(badgeId)).willReturn(true);

        // when & then
        assertThatThrownBy(() -> quests.save(QUEST_1.일반_퀘스트_생성(badgeId)))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 그룹_내_퀘스트가_10개_이상이면_예외를_던진다() {
        // given
        final Long groupId = 1L;
        given(questRepository.countByGroupIdAndStateIn(eq(groupId), any())).willReturn(10);

        // when & then
        assertThatThrownBy(() -> quests.save(QUEST_1.그룹_퀘스트_생성(groupId)))
                .isInstanceOf(InvalidDomainException.class);
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

    @Test
    void ID를_통해_퀘스트_존재를_검증한다() {
        // given
        final Long questId = 1L;
        given(questRepository.existsById(questId)).willReturn(true);

        // when
        quests.validateExistentById(questId);

        // then
        then(questRepository).should().existsById(questId);
    }

    @Test
    void ID를_통한_퀘스트_존재_검증_시_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> quests.validateExistentById(1L))
                .isInstanceOf(NotExistQuestException.class);
    }

    @Test
    void ID를_통해_라벨을_조회한다() {
        // given
        final Long questId = 1L;
        final Quest quest = QUEST_1.일반_퀘스트_생성();
        QUEST_1.보상_없이_세부사항을_설정한다(quest);
        given(questRepository.findById(questId)).willReturn(Optional.of(quest));

        // when
        final String label = quests.getLabelById(questId);

        // then
        assertThat(label).isEqualTo(QUEST_1.label);
    }

    @Test
    void ID를_통한_라벨_조회_시_퀘스트가_없다면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> quests.getLabelById(1L))
                .isInstanceOf(NotExistQuestException.class);
    }
}
