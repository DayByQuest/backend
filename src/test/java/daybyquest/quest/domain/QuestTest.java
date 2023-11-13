package daybyquest.quest.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_LABELED;
import static daybyquest.global.error.ExceptionCode.CANNOT_PARTICIPATE;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_CONTENT;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_EXPIRED_AT;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_IMAGES;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_IMAGE_DESCRIPTION;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_NAME;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_REWARD;
import static daybyquest.global.error.ExceptionCode.INVALID_QUEST_REWARD_COUNT;
import static daybyquest.global.error.ExceptionCode.NOT_EXIST_GROUP;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        void 사진_설명이_비어있으면_예외를_던진다() {
            // given
            final String imageDescription = "";

            // when & then
            assertThatThrownBy(() ->
                    Quest.createNormalQuest(null, imageDescription, QUEST_1.사진_목록(), QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGE_DESCRIPTION.getMessage());
        }

        @Test
        void 사진_설명이_100자를_넘으면_예외를_던진다() {
            // given
            final String imageDescription = 문자열을_만든다(101);

            // when & then
            assertThatThrownBy(() ->
                    Quest.createNormalQuest(null, imageDescription, QUEST_1.사진_목록(), QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGE_DESCRIPTION.getMessage());
        }

        @Test
        void 사진이_3개_보다_많으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"),
                    new Image("사진3"), new Image("사진4"));

            // when & then
            assertThatThrownBy(() ->
                    Quest.createNormalQuest(null, QUEST_1.imageDescription, images, QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGES.getMessage());
        }

        @Test
        void 사진이_3개_보다_적으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"));

            // when & then
            assertThatThrownBy(() ->
                    Quest.createNormalQuest(null, QUEST_1.imageDescription, images, QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGES.getMessage());
        }
    }

    @Nested
    class 그룹_퀘스트_생성은 {

        @Test
        void 그룹_ID가_없으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() ->
                    Quest.createGroupQuest(null, QUEST_1.interest, QUEST_1.사진_목록(), QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(NOT_EXIST_GROUP.getMessage());
        }

        @Test
        void 사진_설명이_비어있으면_예외를_던진다() {
            // given
            final String imageDescription = "";

            // when & then
            assertThatThrownBy(() ->
                    Quest.createGroupQuest(1L, imageDescription, QUEST_1.사진_목록(), QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGE_DESCRIPTION.getMessage());
        }

        @Test
        void 사진_설명이_100자를_넘으면_예외를_던진다() {
            // given
            final String imageDescription = 문자열을_만든다(101);

            // when & then
            assertThatThrownBy(() ->
                    Quest.createGroupQuest(1L, imageDescription, QUEST_1.사진_목록(), QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGE_DESCRIPTION.getMessage());
        }

        @Test
        void 사진이_3개_보다_많으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"),
                    new Image("사진3"), new Image("사진4"));

            // when & then
            assertThatThrownBy(() ->
                    Quest.createGroupQuest(1L, QUEST_1.imageDescription, images, QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGES.getMessage());
        }

        @Test
        void 사진이_3개_보다_적으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"));

            // when & then
            assertThatThrownBy(() ->
                    Quest.createGroupQuest(1L, QUEST_1.imageDescription, images, QUEST_1.사진()))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_IMAGES.getMessage());
        }
    }

    @Nested
    class 세부_사항_설정은 {

        @Test
        void 이미_활성화된_퀘스트라면_예외를_던진다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성();
            QUEST_1.보상_없이_세부사항을_설정한다(quest);

            // when & then
            assertThatThrownBy(() -> QUEST_1.보상_없이_세부사항을_설정한다(quest))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(ALREADY_LABELED.getMessage());
        }

        @Test
        void 제목이_비어있으면_예외를_던진다() {
            // given
            final String title = "";
            final Quest quest = QUEST_1.그룹_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(title, QUEST_1.content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, null))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_NAME.getMessage());
        }

        @Test
        void 제목이_30자를_넘으면_예외를_던진다() {
            // given
            final String title = 문자열을_만든다(31);
            final Quest quest = QUEST_1.그룹_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(title, QUEST_1.content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, null))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_NAME.getMessage());
        }

        @Test
        void 내용이_300자를_넘으면_예외를_던진다() {
            // given
            final String content = 문자열을_만든다(501);
            final Quest quest = QUEST_1.그룹_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(QUEST_1.title, content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, null))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_CONTENT.getMessage());
        }

        @Test
        void 만료_시간이_현재보다_전이면_예외를_던진다() {
            // given
            final LocalDateTime expiredAt = LocalDateTime.now().minusDays(1);
            final Quest quest = QUEST_1.그룹_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(QUEST_1.title, QUEST_1.content, QUEST_1.interest, expiredAt,
                            QUEST_1.label, null))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_EXPIRED_AT.getMessage());
        }

        @ParameterizedTest
        @ValueSource(longs = {0L, -1L, -1111L, 366L, 1000L})
        void 목표치가_1에서_365가_아니면_예외를_던진다(final Long rewardCount) {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(QUEST_1.title, QUEST_1.content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, rewardCount))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_REWARD_COUNT.getMessage());
        }

        @Test
        void 보상이_없는데_목표치가_있으면_예외를_던진다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성();

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(QUEST_1.title, QUEST_1.content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, QUEST_1.rewardCount))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_REWARD.getMessage());
        }

        @Test
        void 보상이_있는데_목표치가_없으면_예외를_던진다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(
                    () -> quest.setDetail(QUEST_1.title, QUEST_1.content, QUEST_1.interest, QUEST_1.expiredAt,
                            QUEST_1.label, null))
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(INVALID_QUEST_REWARD.getMessage());
        }
    }

    @Nested
    class 참가_가능_여부_검증은 {

        @Test
        void 라벨_필요_상태라면_예외를_던진다() {
            // given
            final Quest quest = QUEST_1.일반_퀘스트_생성(1L);

            // when & then
            assertThatThrownBy(quest::validateCanParticipate)
                    .isInstanceOf(InvalidDomainException.class)
                    .hasMessageContaining(CANNOT_PARTICIPATE.getMessage());
        }

    }
}
