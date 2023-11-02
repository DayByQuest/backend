package daybyquest.interest.dto.response;

import daybyquest.interest.domain.Interest;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InterestResponse {

    private String name;

    private String imageIdentifier;

    private InterestResponse(final String name, final String imageIdentifier) {
        this.name = name;
        this.imageIdentifier = imageIdentifier;
    }

    public static InterestResponse of(final Interest interest) {
        return new InterestResponse(interest.getName(), interest.getImageIdentifier());
    }
}
