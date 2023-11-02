package daybyquest.participant.application;

import daybyquest.badge.domain.Ownings;
import daybyquest.participant.domain.Participant;
import daybyquest.participant.domain.Participants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TakeRewardService {

    private final Participants participants;

    private final Ownings ownings;

    public TakeRewardService(final Participants participants, final Ownings ownings) {
        this.participants = participants;
        this.ownings = ownings;
    }

    @Transactional
    public void invoke(final Long loginId, final Long questId) {
        final Participant participant = participants.getByUserIdAndQuestId(loginId, questId);
        final Long badgeId = participant.takeReward();
        if (badgeId != null) {
            ownings.saveByUserIdAndBadgeId(loginId, badgeId);
        }
    }
}
