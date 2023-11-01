package daybyquest.participant.domain;

import static daybyquest.global.error.ExceptionCode.NOT_FINISHABLE_QUEST;
import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.participant.domain.ParticipantState.FINISHED;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.quest.domain.Quest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ParticipantState state;

    public Participant(Long userId, Quest quest) {
        this.userId = userId;
        this.quest = quest;
        this.state = DOING;
    }

    public Long getQuestId() {
        return quest.getId();
    }

    public void takeReward() {
        if (state == FINISHED || state == CONTINUE) {
            throw new InvalidDomainException(NOT_FINISHABLE_QUEST);
        }
        state = FINISHED;
    }

    public void finish() {
        if (state != CONTINUE) {
            throw new InvalidDomainException(NOT_FINISHABLE_QUEST);
        }
        state = FINISHED;
    }

    public void doContinue() {
        if (state != FINISHED) {
            throw new InvalidDomainException(NOT_FINISHABLE_QUEST);
        }
        state = CONTINUE;
    }
}
