package daybyquest.user.application;

import daybyquest.interest.domain.InterestValidator;
import daybyquest.user.domain.User;
import daybyquest.user.domain.UserRepository;
import daybyquest.user.dto.request.UpdateUserInterestRequest;
import java.util.Collection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserInterestService {

    private final UserRepository userRepository;


    private final InterestValidator interestValidator;

    public UpdateUserInterestService(final UserRepository userRepository,
        final InterestValidator interestValidator) {
        this.userRepository = userRepository;
        this.interestValidator = interestValidator;
    }

    @Transactional
    public void invoke(final Long loginId, final UpdateUserInterestRequest request) {
        final User user = userRepository.getById(loginId);
        final Collection<String> interests = request.getInterests();
        interestValidator.validateInterests(interests);
        user.updateInterests(interests);
    }
}
