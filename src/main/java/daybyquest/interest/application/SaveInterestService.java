package daybyquest.interest.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.vo.Image;
import daybyquest.image.vo.ImageIdentifierGenerator;
import daybyquest.image.vo.Images;
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
        final String identifier = generator.generateIdentifier(CATEGORY, file.getOriginalFilename());
        images.upload(identifier, MultipartFileUtils.getInputStream(file));
        final Interest interest = new Interest(request.getName(), new Image(identifier));
        interests.save(interest);
    }
}