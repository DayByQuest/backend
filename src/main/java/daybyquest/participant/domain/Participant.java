package daybyquest.participant.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(ParticipantId.class)
public class Participant {

    @Id
    private Long userId;

    @Id
    private Long questId;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ParticipantState state;

    public Participant(Long userId, Long questId) {
        this.userId = userId;
        this.questId = questId;
        this.state = ParticipantState.DOING;
    }
}
