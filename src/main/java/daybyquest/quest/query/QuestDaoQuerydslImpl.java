package daybyquest.quest.query;

import static daybyquest.participant.domain.QParticipant.participant;
import static daybyquest.post.domain.QPost.post;
import static daybyquest.quest.domain.QQuest.quest;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistQuestException;
import daybyquest.participant.domain.ParticipantState;
import org.springframework.stereotype.Repository;

@Repository
public class QuestDaoQuerydslImpl implements QuestDao {

    private final JPAQueryFactory factory;

    public QuestDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public QuestData getById(final Long userId, final Long id) {
        final QuestData data = factory.select(Projections.constructor(QuestData.class,
                        quest.id,
                        quest.title,
                        quest.content,
                        quest.interestName,
                        quest.category,
                        quest.expiredAt,
                        quest.rewardCount,
                        JPAExpressions.select(post.count())
                                .from(post)
                                .where(post.userId.eq(userId), post.questId.eq(id))
                ))
                .from(quest)
                .fetchOne();
        if (data == null) {
            throw new NotExistQuestException();
        }

        ParticipantState state = factory.select(participant.state)
                .from(participant)
                .where(participant.userId.eq(userId), participant.questId.eq(id))
                .fetchOne();
        if (state == null) {
            state = ParticipantState.NOT;
        }
        data.setState(state);
        return data;
    }
}
