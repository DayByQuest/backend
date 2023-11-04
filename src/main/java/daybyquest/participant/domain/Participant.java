package daybyquest.participant.domain;

import static daybyquest.global.error.ExceptionCode.ALREADY_REWARDED_QUEST;
import static daybyquest.global.error.ExceptionCode.NOT_CONTINUABLE_QUEST;
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
    private Quest quest;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ParticipantState state;

    private Long linkedCount;

    public Participant(Long userId, Quest quest) {
        this.userId = userId;
        this.quest = quest;
        this.state = DOING;
        this.linkedCount = 0L;
    }

    public Long getQuestId() {
        return quest.getId();
    }

    public Long takeReward() {
        validateDidNotTakeReward();
        validateRewardCount();
        state = FINISHED;
        return quest.getBadgeId();
    }

    private void validateDidNotTakeReward() {
        if (state == FINISHED || state == CONTINUE) {
            throw new InvalidDomainException(ALREADY_REWARDED_QUEST);
        }
    }

    private void validateRewardCount() {
        if (quest.getRewardCount() == null || quest.getRewardCount() == 0
                || linkedCount < quest.getRewardCount()) {
            throw new InvalidDomainException(NOT_FINISHABLE_QUEST);
        }
    }

    public void finish() {
        if (state != CONTINUE) {
            throw new InvalidDomainException(NOT_FINISHABLE_QUEST);
        }
        state = FINISHED;
    }

    public void doContinue() {
        if (state != FINISHED) {
            throw new InvalidDomainException(NOT_CONTINUABLE_QUEST);
        }
        state = CONTINUE;
    }

    public void increaseLinkedCount() {
        if (state != DOING && state != CONTINUE) {
            throw new InvalidDomainException();
        }
        linkedCount += 1;
    }
}
