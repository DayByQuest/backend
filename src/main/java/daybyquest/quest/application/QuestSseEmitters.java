package daybyquest.quest.application;

import daybyquest.global.error.exception.InternalServerException;
import daybyquest.quest.dto.response.QuestLabelsResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Component
@Slf4j
public class QuestSseEmitters {

    private static final Long SSE_TIMEOUT = 10L * 1000 * 60;

    private static final String MESSAGE_NAME = "labels";

    private static final String CONNECT_MESSAGE_NAME = "connect";

    private static final String CONNECT_MESSAGE_CONTENT = "success";

    private final Map<Long, SseEmitter> emitters;

    private final Map<Long, QuestLabelsResponse> cache;

    public QuestSseEmitters() {
        this.emitters = new ConcurrentHashMap<>();
        this.cache = new ConcurrentHashMap<>();
    }

    public SseEmitter subscribe(final Long questId) {
        final SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);
        emitter.onCompletion(() -> {
            log.debug("QuestSseEmiiter finished with completiong. Quest ID: {}", questId);
            emitters.remove(questId);
        });
        emitter.onTimeout(() -> {
            log.debug("QuestSseEmiiter finished with timeout. Quest ID: {}", questId);
            emitter.complete();
        });
        emitters.put(questId, emitter);
        sendConnectedMessage(emitter);
        sendCachedMessage(questId);
        return emitter;
    }

    private void sendConnectedMessage(final SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().name(CONNECT_MESSAGE_NAME).data(CONNECT_MESSAGE_CONTENT));
        } catch (final IOException e) {
            log.error("IOException occurred while sending connected message.");
            log.error(e.getMessage());
        }
    }

    private void sendCachedMessage(final Long questId) {
        if (cache.containsKey(questId)) {
            send(questId, cache.get(questId));
            cache.remove(questId);
        }
    }

    public void send(final Long questId, final QuestLabelsResponse content) {
        if (emitters.containsKey(questId)) {
            final SseEmitter emitter = emitters.get(questId);
            try {
                emitter.send(SseEmitter.event().name(MESSAGE_NAME).data(content));
            } catch (final IOException e) {
                log.error("IOException occurred while sending quest labels.: quest id {}", questId);
                throw new InternalServerException();
            }
            return;
        }
        cache.put(questId, content);
    }
}
