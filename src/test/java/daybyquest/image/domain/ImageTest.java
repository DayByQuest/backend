package daybyquest.image.domain;

import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    void 식별자가_없으면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> new Image(""))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 식별자가_255자를_넘으면_예외를_던진다() {
        // given
        final String identifier = 문자열을_만든다(256);

        // when & then
        assertThatThrownBy(() -> new Image(identifier))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 식별자가_다르면_비교_시_True를_반환한다() {
        // given
        final String identifier = "식별자";
        final Image image1 = new Image(identifier);
        final Image image2 = new Image(identifier);

        // when
        final boolean actual = image1.equals(image2);

        // then
        assertThat(actual).isTrue();
    }

    @Test
    void 식별자가_다르면_비교_시_False를_반환한다() {
        // given
        final Image image1 = new Image("식별자1");
        final Image image2 = new Image("식별자2");

        // when
        final boolean actual = image1.equals(image2);

        // then
        assertThat(actual).isFalse();
    }
}
