package daybyquest.participant.domain;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantId implements Serializable {

    private Long questId;

    private Long userId;

}
