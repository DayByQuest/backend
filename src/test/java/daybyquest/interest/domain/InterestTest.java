package daybyquest.interest.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.vo.Image;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class InterestTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육칠팔구십일"})
    void 이름이_1에서_20글자가_아니면_예외를_던진다(final String name) {
        assertThatThrownBy(() -> new Interest(name, new Image("사진")))
                .isInstanceOf(InvalidDomainException.class);
    }
}
