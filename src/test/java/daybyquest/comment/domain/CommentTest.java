package daybyquest.comment.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
            final String content = Stream.generate(() -> "x").limit(201).collect(Collectors.joining());

            // when & then
            assertThatThrownBy(() -> new Comment(1L, 1L, content))
                    .isInstanceOf(InvalidDomainException.class);
        }
    }

}
