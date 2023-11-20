package daybyquest.image.application;

import daybyquest.global.utils.MultipartFileUtils;
import daybyquest.image.domain.Image;
import daybyquest.image.domain.ImageIdentifierGenerator;
import daybyquest.image.domain.ImageRemovedEvent;
import daybyquest.image.domain.ImageSavedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    private final ImageIdentifierGenerator generator;

    private final ApplicationEventPublisher publisher;

    public ImageService(final ImageIdentifierGenerator generator, final ApplicationEventPublisher publisher) {
        this.generator = generator;
        this.publisher = publisher;
    }

    public Image convertToImage(final String category, final MultipartFile file) {
        final String identifier = generator.generate(category, file.getOriginalFilename());
        publisher.publishEvent(new ImageSavedEvent(identifier, MultipartFileUtils.getInputStream(file)));
        return new Image(identifier);
    }

    public void remove(final String identifier) {
        publisher.publishEvent(new ImageRemovedEvent(identifier));
    }
}
