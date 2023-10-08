package daybyquest.participant.domain;

import org.springframework.data.repository.Repository;

public interface ParticipantRepository extends Repository<Participant, ParticipantId> {

    Participant save(Participant participant);

}