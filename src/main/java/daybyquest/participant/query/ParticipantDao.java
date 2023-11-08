package daybyquest.participant.query;

import daybyquest.participant.domain.ParticipantState;
import java.util.List;

public interface ParticipantDao {

    List<Long> findIdsByUserIdAndState(final Long userId, final ParticipantState state);
}
