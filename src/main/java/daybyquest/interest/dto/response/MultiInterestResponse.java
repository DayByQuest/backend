package daybyquest.interest.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MultiInterestResponse {

    private List<InterestResponse> interests;

    public MultiInterestResponse(final List<InterestResponse> interests) {
        this.interests = interests;
    }
}
