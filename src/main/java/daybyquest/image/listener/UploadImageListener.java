package daybyquest.image.listener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

import daybyquest.image.domain.ImageSavedEvent;
import daybyquest.image.domain.Images;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class UploadImageListener {

    private final Images images;

    public UploadImageListener(final Images images) {
        this.images = images;
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void listenImageSavedEvent(final ImageSavedEvent event) {
        log.debug("S3 Upload. identifier: {}", event.identifier());
        images.upload(event.identifier(), event.inputStream());
    }
}
