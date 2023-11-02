package daybyquest.interest.presentation;

import daybyquest.interest.application.GetInterestsService;
import daybyquest.interest.dto.response.MultiInterestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InterestQueryApi {

    private final GetInterestsService getInterestsService;

    public InterestQueryApi(final GetInterestsService getInterestsService) {
        this.getInterestsService = getInterestsService;
    }

    @GetMapping("/interest")
    public ResponseEntity<MultiInterestResponse> getInterests() {
        final MultiInterestResponse response = getInterestsService.invoke();
        return ResponseEntity.ok(response);
    }
}
