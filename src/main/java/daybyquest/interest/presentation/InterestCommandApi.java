package daybyquest.interest.presentation;

import daybyquest.auth.Authorization;
import daybyquest.auth.domain.AccessUser;
import daybyquest.interest.application.SaveInterestService;
import daybyquest.interest.dto.request.SaveInterestRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InterestCommandApi {

    private final SaveInterestService saveInterestService;

    public InterestCommandApi(final SaveInterestService saveInterestService) {
        this.saveInterestService = saveInterestService;
    }

    @PostMapping("/interest")
    @Authorization(admin = true)
    public ResponseEntity<Void> saveInterest(final AccessUser user,
            @RequestPart("image") final MultipartFile file,
            @RequestPart("request") @Valid final SaveInterestRequest request) {
        saveInterestService.invoke(request, file);
        return ResponseEntity.ok().build();
    }
}
