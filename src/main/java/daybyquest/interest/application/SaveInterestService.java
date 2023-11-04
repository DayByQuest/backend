package daybyquest.interest.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.domain.Image;
import daybyquest.image.domain.ImageIdentifierGenerator;
import daybyquest.image.domain.Images;
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

    private final Images images;

    private final ImageIdentifierGenerator generator;

    public SaveInterestService(final Interests interests, final Images images,
            final ImageIdentifierGenerator generator) {
        this.interests = interests;
        this.images = images;
        this.generator = generator;
    }

    @Transactional
    public void invoke(final SaveInterestRequest request, final MultipartFile file) {
        final String identifier = generator.generate(CATEGORY, file.getOriginalFilename());
        final Image image = images.upload(identifier, MultipartFileUtils.getInputStream(file));
        final Interest interest = new Interest(request.getName(), image);
        interests.save(interest);
    }
}
