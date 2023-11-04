package daybyquest.quest.domain;

import static daybyquest.quest.domain.QuestState.ACTIVE;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class QuestTest {

    @Nested
    class 일반_퀘스트_생성은 {

        @Test
        void 제목이_비어있으면_예외를_던진다() {
            // given
            final String title = "";

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 제목이_30자를_넘으면_예외를_던진다() {
            // given
            final String title = 문자열을_만든다(31);

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 내용이_300자를_넘으면_예외를_던진다() {
            // given
            final String content = 문자열을_만든다(501);

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진_설명이_비어있으면_예외를_던진다() {
            // given
            final String imageDescription = "";

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진_설명이_100자를_넘으면_예외를_던진다() {
            // given
            final String imageDescription = 문자열을_만든다(101);

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @ParameterizedTest
        @ValueSource(longs = {0L, -1L, -1111L})
        void 목표치가_1보다_작으면_예외를_던진다(final Long rewardCount) {
            // given & when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, rewardCount, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @ParameterizedTest
        @ValueSource(longs = {366L, 1000L})
        void 목표치가_365보다_크면_예외를_던진다(final Long rewardCount) {
            // given & when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, rewardCount, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_3개_보다_많으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"),
                    new Image("사진3"), new Image("사진4"));

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, images))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_3개_보다_적으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"));

            // when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, images))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 보상이_없는데_목표치가_있으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, 1L, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 보상이_있는데_목표치가_없으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> Quest.createNormalQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }

    @Nested
    class 그룹_퀘스트_생성은 {

        @Test
        void 그룹_ID가_없으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(null, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 제목이_비어있으면_예외를_던진다() {
            // given
            final String title = "";

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 제목이_30자를_넘으면_예외를_던진다() {
            // given
            final String title = 문자열을_만든다(31);

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, title,
                    QUEST_1.content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 내용이_300자를_넘으면_예외를_던진다() {
            // given
            final String content = 문자열을_만든다(501);

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    content, null, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진_설명이_비어있으면_예외를_던진다() {
            // given
            final String imageDescription = "";

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진_설명이_100자를_넘으면_예외를_던진다() {
            // given
            final String imageDescription = 문자열을_만든다(101);

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_3개_보다_많으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"),
                    new Image("사진3"), new Image("사진4"));

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, images))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_3개_보다_적으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"));

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, null, QUEST_1.imageDescription, images))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 만료_시간이_현재보다_전이면_예외를_던진다() {
            // given
            final LocalDateTime expiredAt = LocalDateTime.now().minusDays(1);

            // when & then
            assertThatThrownBy(() -> Quest.createGroupQuest(1L, QUEST_1.interest, QUEST_1.title,
                    QUEST_1.content, expiredAt, QUEST_1.imageDescription, QUEST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }

    @Nested
    class 라벨_설정은 {

        @Test
        void 새로_만든_퀘스트였다면_퀘스트를_활성화하고_라벨을_설정한다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성();

            // when
            quest.setLabel(QUEST_1.label);

            // then
            assertAll(() -> {
                assertThat(quest.getState()).isEqualTo(ACTIVE);
                assertThat(quest.getLabel()).isEqualTo(QUEST_1.label);
            });
        }

        @Test
        void 이미_활성화된_퀘스트라면_예외를_던진다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성();
            quest.setLabel(QUEST_1.label);

            // when & then
            assertThatThrownBy(() -> quest.setLabel(QUEST_1.label))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }
}
