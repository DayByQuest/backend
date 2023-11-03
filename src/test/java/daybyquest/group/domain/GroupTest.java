package daybyquest.group.domain;

import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.util.StringUtils.문자열을_만든다;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import daybyquest.global.error.exception.InvalidDomainException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class GroupTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "일이삼사오육칠팔구십일이삼사오육"})
    void 그룹_이름이_1_에서_15_글자_사이가_아니면_예외를_던진다(final String name) {
        // given & when & then
        assertThatThrownBy(() -> new Group(GROUP_1.interest, name, GROUP_1.description, GROUP_1.대표_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 설명이_200_글자_초과면_예외를_던진다() {
        // given
        final String description = 문자열을_만든다(201);

        // when & then
        assertThatThrownBy(
                () -> new Group(GROUP_1.interest, GROUP_1.name, description, GROUP_1.대표_사진()))
                .isInstanceOf(InvalidDomainException.class);
    }
}
