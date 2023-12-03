package daybyquest.post.domain;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PostTest {

    @Nested
    class 생성자는 {

        @Test
        void 사용자_ID가_없으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> new Post(null, null, POST_1.content, POST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_없으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> new Post(1L, null, POST_1.content, Collections.emptyList()))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 사진이_5_보다_많으면_예외를_던진다() {
            // given
            final List<Image> images = List.of(new Image("사진1"), new Image("사진2"),
                    new Image("사진3"), new Image("사진4"),
                    new Image("사진5"), new Image("사진6"));

            // when & then
            assertThatThrownBy(() -> new Post(1L, null, POST_1.content, images))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 내용이_500_글자_초과면_예외를_던진다() {
            // given
            final String content = 문자열을_만든다(501);

            // when & then
            assertThatThrownBy(() -> new Post(1L, null, content, POST_1.사진_목록()))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }

    @Test
    void 퀘스트_링크를_성공_처리한다() {
        // given
        final Post post = POST_1.생성(1L, 2L);

        // when
        post.success();

        // then
        assertThat(post.getState()).isEqualTo(PostState.SUCCESS);
    }

    @Test
    void 확인_필요_상태_에서_퀘스트_링크를_성공_처리한다() {
        // given
        final Post post = POST_1.생성(1L, 2L);
        post.needCheck();

        // when
        post.success();

        // then
        assertThat(post.getState()).isEqualTo(PostState.SUCCESS);
    }

    @Test
    void 퀘스트_링크_성공_처리_시_이미_판정된_퀘스트라면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L, 2L);
        post.success();

        // when & then
        assertThatThrownBy(post::success)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_링크_성공_처리_시_퀘스트와_연관이_없다면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L);

        // when & then
        assertThatThrownBy(post::success)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_링크를_확인_필요_처리_한다() {
        // given
        final Post post = POST_1.생성(1L, 2L);

        // when
        post.needCheck();

        // then
        assertThat(post.getState()).isEqualTo(PostState.NEED_CHECK);
    }

    @Test
    void 퀘스트_링크_확인_필요_처리_시_이미_판정된_퀘스트라면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L, 2L);
        post.success();

        // when & then
        assertThatThrownBy(post::needCheck)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_링크_확인_필요_처리_시_퀘스트와_연관이_없다면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L);

        // when & then
        assertThatThrownBy(post::needCheck)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_링크를_실패_처리_한다() {
        // given
        final Post post = POST_1.생성(1L, 2L);

        // when
        post.fail();

        // then
        assertThat(post.getState()).isEqualTo(PostState.FAIL);
    }

    @Test
    void 퀘스트_링크_실패_처리_시_미처리나_확인_필요_상태가_아니라면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L, 2L);
        post.success();

        // when & then
        assertThatThrownBy(post::fail)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트_링크_실패_처리_시_퀘스트와_연관이_없다면_예외를_던진다() {
        // given
        final Post post = POST_1.생성(1L);

        // when & then
        assertThatThrownBy(post::fail)
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 퀘스트가_링크_되었는지_확인한다() {
        // given
        final Post linkedPost = POST_1.생성(1L, 2L);
        final Post notLinkedPost = POST_1.생성(1L);

        // when
        final boolean linkedActual = linkedPost.isQuestLinked();
        final boolean notLinkedActual = notLinkedPost.isQuestLinked();

        // then
        assertAll(() -> {
            assertThat(linkedActual).isTrue();
            assertThat(notLinkedActual).isFalse();
        });
    }
}
