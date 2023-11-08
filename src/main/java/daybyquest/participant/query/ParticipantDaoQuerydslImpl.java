package daybyquest.participant.query;

import static daybyquest.participant.domain.QParticipant.participant;
import static daybyquest.quest.domain.QQuest.quest;

import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.participant.domain.ParticipantState;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDaoQuerydslImpl implements ParticipantDao {

    private final JPAQueryFactory factory;

    public ParticipantDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Long> findIdsByUserIdAndState(final Long userId,
            final ParticipantState state) {
        return factory.select(quest.id)
                .from(participant)
                .innerJoin(participant.quest, quest)
                .where(participant.userId.eq(userId), participant.state.eq(state))
                .fetch();
    }
}
