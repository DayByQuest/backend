package daybyquest.interest.application;

import daybyquest.image.application.ImageService;
import daybyquest.image.domain.Image;
import daybyquest.interest.domain.Interest;
import daybyquest.interest.domain.Interests;
import daybyquest.interest.dto.request.SaveInterestRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SaveInterestService {

    private static final String CATEGORY = "INTEREST";

    private final Interests interests;

    private final ImageService imageService;

    public SaveInterestService(final Interests interests, final ImageService imageService) {
        this.interests = interests;
        this.imageService = imageService;
    }

    @Transactional
    public void invoke(final SaveInterestRequest request, final MultipartFile file) {
        final Image image = imageService.convertToImage(CATEGORY, file);
        final Interest interest = new Interest(request.getName(), image);
        interests.save(interest);
    }
}
