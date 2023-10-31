package daybyquest.participant.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

interface ParticipantRepository extends Repository<Participant, ParticipantId> {

    Participant save(Participant participant);

    Optional<Participant> findByUserIdAndQuestId(final Long userId, final Long questId);

    boolean existsByUserIdAndQuestId(final Long userId, final Long questId);

    void delete(final Participant participant);
}