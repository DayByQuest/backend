package daybyquest.quest.domain;

import java.util.Collection;
import java.util.Optional;
import org.springframework.data.repository.Repository;

interface QuestRepository extends Repository<Quest, Long> {

    Quest save(final Quest quest);

    Optional<Quest> findById(final Long id);

    boolean existsById(final Long id);

    boolean existsByBadgeId(final Long badgeId);

    int countByGroupIdAndStateIn(final Long groupId, final Collection<QuestState> state);
}