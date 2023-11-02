package daybyquest.interest.application;

import daybyquest.interest.domain.Interests;
import daybyquest.interest.dto.response.InterestResponse;
import daybyquest.interest.dto.response.MultiInterestResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetInterestsService {

    private final Interests interests;

    public GetInterestsService(final Interests interests) {
        this.interests = interests;
    }

    @Transactional(readOnly = true)
    public MultiInterestResponse invoke() {
        return new MultiInterestResponse(interests.findAll().stream().map(InterestResponse::of).toList());
    }
}
