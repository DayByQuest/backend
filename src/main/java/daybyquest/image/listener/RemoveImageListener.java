package daybyquest.image.listener;

import static org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT;

import daybyquest.image.domain.ImageRemovedEvent;
import daybyquest.image.domain.Images;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class RemoveImageListener {

    private final Images images;

    public RemoveImageListener(final Images images) {
        this.images = images;
    }

    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void listenImageRemovedEvent(final ImageRemovedEvent event) {
        log.debug("S3 delete. identifier: {}", event.identifier());
        images.remove(event.identifier());
    }
}
