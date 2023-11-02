package daybyquest.interest.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_EXIST_INTEREST;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.error.exception.NotExistInterestException;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Interests {

    private final InterestRepository interestRepository;

    Interests(final InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public void save(final Interest interest) {
        validateNotExistentByName(interest.getName());
        interestRepository.save(interest);
    }

    public void validateNotExistentByName(final String interestName) {
        if (interestRepository.existsByName(interestName)) {
            throw new InvalidDomainException(ALREADY_EXIST_INTEREST);
        }
    }

    public void validateInterest(final String interestName) {
        if (!interestRepository.existsByName(interestName)) {
            throw new NotExistInterestException();
        }
    }

    public void validateInterests(final Collection<String> interestNames) {
        final List<Interest> interests = interestRepository.findAllByNameIn(interestNames);
        if (interests.size() != interestNames.size()) {
            throw new NotExistInterestException();
        }
        interests.forEach(
                interest -> validateContainsInterest(interestNames, interest)
        );
    }

    private void validateContainsInterest(final Collection<String> interestStrings, final Interest interest) {
        if (!interestStrings.contains(interest.getName())) {
            throw new NotExistInterestException();
        }
    }

    public List<Interest> findAll() {
        return interestRepository.findAll();
    }
}
