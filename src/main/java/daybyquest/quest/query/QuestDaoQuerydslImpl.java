package daybyquest.quest.query;

import static daybyquest.group.domain.QGroup.group;
import static daybyquest.participant.domain.QParticipant.participant;
import static daybyquest.post.domain.PostState.SUCCESS;
import static daybyquest.post.domain.QPost.post;
import static daybyquest.quest.domain.QQuest.quest;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import daybyquest.global.error.exception.NotExistQuestException;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class QuestDaoQuerydslImpl implements QuestDao {

    private final JPAQueryFactory factory;

    public QuestDaoQuerydslImpl(final JPAQueryFactory factory) {
        this.factory = factory;
    }

    @Override
    public QuestData getById(final Long userId, final Long id) {
        final QuestData data = factory.select(projectQuestData(userId, quest.id))
                .from(quest)
                .leftJoin(group).on(group.id.eq(quest.groupId))
                .where(quest.id.eq(id))
                .fetchOne();
        if (data == null) {
            throw new NotExistQuestException();
        }
        return data;
    }

    private ConstructorExpression<QuestData> projectQuestData(final Long userId, final NumberPath<Long> id) {
        return Projections.constructor(QuestData.class,
                quest.id,
                quest.groupId,
                quest.badgeId,
                quest.title,
                quest.content,
                quest.interestName,
                quest.category,
                JPAExpressions.select(participant.state)
                        .from(participant)
                        .where(participant.userId.eq(userId), participant.quest.id.eq(id)),
                quest.expiredAt,
                quest.image,
                quest.rewardCount,
                JPAExpressions.select(post.count())
                        .from(post)
                        .where(post.userId.eq(userId), post.questId.eq(id), post.state.eq(SUCCESS)),
                group.name
        );
    }

    @Override
    public List<QuestData> findAllByIdIn(final Long userId, final Collection<Long> ids) {
        return factory.select(projectQuestData(userId, quest.id))
                .from(quest)
                .leftJoin(group).on(group.id.eq(quest.groupId))
                .where(quest.id.in(ids))
                .fetch();
    }
}
