package daybyquest.comment.domain;

import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CommentTest {

    @Nested
    class 생성자는 {

        @Test
        void 내용이_비어있으면_예외를_던진다() {
            // given & when & then
            assertThatThrownBy(() -> new Comment(1L, 1L, ""))
                    .isInstanceOf(InvalidDomainException.class);
        }

        @Test
        void 내용이_200_글자_초과면_예외를_던진다() {
            // given
            final String content = 문자열을_만든다(201);

            // when & then
            assertThatThrownBy(() -> new Comment(1L, 1L, content))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }

}
