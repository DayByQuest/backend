package daybyquest.post.domain;

import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.vo.Image;
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
}
