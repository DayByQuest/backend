package daybyquest.interest.domain;

import daybyquest.global.error.exception.NotExistInterestException;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class InterestValidator {

    private final InterestRepository interestRepository;

    public InterestValidator(final InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public void validateInterests(final Collection<String> interestStrings) {
        final List<Interest> interests = interestRepository.findAllByNameIn(interestStrings);
        if (interests.size() != interestStrings.size()) {
            throw new NotExistInterestException();
        }
        interests.forEach(
            interest -> validateContainsInterest(interestStrings, interest)
        );
    }

    private void validateContainsInterest(final Collection<String> interestStrings, final Interest interest) {
        if (!interestStrings.contains(interest.getName())) {
            throw new NotExistInterestException();
        }
    }
}
