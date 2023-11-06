package daybyquest.interest.dto.response;

import daybyquest.interest.domain.Interest;

public record InterestResponse(String name, String imageIdentifier) {

    public static InterestResponse of(final Interest interest) {
        return new InterestResponse(interest.getName(), interest.getImageIdentifier());
    }
}
