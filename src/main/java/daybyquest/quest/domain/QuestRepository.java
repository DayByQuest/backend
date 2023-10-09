package daybyquest.quest.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface QuestRepository extends Repository<Quest, Long> {

    Quest save(Quest quest);

    Optional<Quest> findById(Long id);

}