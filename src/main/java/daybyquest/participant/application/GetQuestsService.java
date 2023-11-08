package daybyquest.participant.application;

import daybyquest.participant.domain.ParticipantState;
import daybyquest.participant.query.ParticipantDao;
import daybyquest.quest.dto.response.MultipleQuestsResponse;
import daybyquest.quest.dto.response.QuestResponse;
import daybyquest.quest.query.QuestDao;
import daybyquest.quest.query.QuestData;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GetQuestsService {

    private final ParticipantDao participantDao;

    private final QuestDao questDao;

    public GetQuestsService(final ParticipantDao participantDao, final QuestDao questDao) {
        this.participantDao = participantDao;
        this.questDao = questDao;
    }

    @Transactional(readOnly = true)
    public MultipleQuestsResponse invoke(final Long loginId, final ParticipantState state) {
        final List<Long> ids = participantDao.findIdsByUserIdAndState(loginId, state);
        final List<QuestData> questData = questDao.findAllByIdIn(loginId, ids);
        final List<QuestResponse> responses = questData.stream().map(QuestResponse::of).toList();
        return new MultipleQuestsResponse(responses);
    }
}
