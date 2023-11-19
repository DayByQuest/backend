package daybyquest.participant.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

interface ParticipantRepository extends Repository<Participant, ParticipantId> {

    Participant save(Participant participant);

    @Query("SELECT p FROM Participant p WHERE p.userId=:userId and p.quest.id = :questId")
    Optional<Participant> findByUserIdAndQuestId(final Long userId, final Long questId);

    @Query("SELECT count (p) > 0 FROM Participant p WHERE p.userId=:userId and p.quest.id = :questId")
    boolean existsByUserIdAndQuestId(final Long userId, final Long questId);

    void delete(final Participant participant);

    int countByUserIdAndState(final Long userId, final ParticipantState state);
}