package daybyquest.interest.domain;

import static daybyquest.support.fixture.InterestFixtures.INTERST_1;
import static daybyquest.support.fixture.InterestFixtures.INTERST_2;
import static daybyquest.support.fixture.InterestFixtures.INTERST_3;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistInterestException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class InterestsTest {

    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private Interests interests;

    @Test
    void 이름_중복_검사를_하고_저장한다() {
        // given & when
        interests.save(INTERST_1.생성());

        // then
        assertAll(() -> {
            then(interestRepository).should().existsByName(INTERST_1.name);
            then(interestRepository).should().save(any(Interest.class));
        });
    }

    @Test
    void 저장_시_이름이_이미_있다면_예외를_던진다() {
        // given
        given(interestRepository.existsByName(INTERST_1.name)).willReturn(true);

        // then
        assertThatThrownBy(() -> interests.save(INTERST_1.생성()))
                .isInstanceOf(InvalidDomainException.class);
    }

    @Test
    void 이름을_통해_존재_여부를_검증한다() {
        // given
        given(interestRepository.existsByName(INTERST_1.name)).willReturn(true);

        // when
        interests.validateInterest(INTERST_1.name);

        // then
        then(interestRepository).should().existsByName(INTERST_1.name);
    }

    @Test
    void 이름을_통해_존재_여부를_검증_시_존재한다면_에외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> interests.validateInterest(INTERST_1.name))
                .isInstanceOf(NotExistInterestException.class);
    }

    @Test
    void 여러_이름을_통해_존재_여부를_검증한다() {
        // given
        final List<String> names = List.of(INTERST_1.name, INTERST_2.name, INTERST_3.name);
        final List<Interest> expected = List.of(INTERST_1.생성(), INTERST_2.생성(), INTERST_3.생성());
        given(interestRepository.findAllByNameIn(names)).willReturn(expected);

        // when
        interests.validateInterests(names);

        // then
        then(interestRepository).should().findAllByNameIn(names);
    }


    @Test
    void 여러_이름을_통해_존재_여부를_검증_시_하나라도_없다면_예외를_던진다() {
        // given
        final List<String> names = List.of(INTERST_1.name, INTERST_2.name, INTERST_3.name);
        final List<Interest> expected = List.of(INTERST_1.생성(), INTERST_2.생성());
        given(interestRepository.findAllByNameIn(names)).willReturn(expected);

        // when
        assertThatThrownBy(() -> interests.validateInterests(names))
                .isInstanceOf(NotExistInterestException.class);
    }
}
