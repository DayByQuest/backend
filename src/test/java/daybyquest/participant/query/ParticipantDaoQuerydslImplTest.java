package daybyquest.participant.query;

import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.participant.domain.ParticipantState.FINISHED;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.QuestFixtures.QUEST_2;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_계속한다;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_끝낸다;
import static org.assertj.core.api.Assertions.assertThat;

import daybyquest.participant.domain.Participant;
import daybyquest.quest.domain.Quest;
import daybyquest.support.test.QuerydslTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(ParticipantDaoQuerydslImpl.class)
public class ParticipantDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private ParticipantDaoQuerydslImpl participantDao;

    @Test
    void 진행중인_퀘스트_ID들을_조회한다() {
        // given
        final Long userId = 1L;

        final Quest 진행중인_퀘스트_1 = 저장한다(QUEST_1.일반_퀘스트_생성());
        final Quest 끝난_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 계속하는_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 진행중인_퀘스트_2 = 저장한다(QUEST_2.일반_퀘스트_생성());

        저장한다(new Participant(userId, 진행중인_퀘스트_1));
        퀘스트를_끝낸다(저장한다(new Participant(userId, 끝난_퀘스트)));
        퀘스트를_계속한다(저장한다(new Participant(userId, 계속하는_퀘스트)));
        저장한다(new Participant(userId, 진행중인_퀘스트_2));
        저장한다(QUEST_1.일반_퀘스트_생성());

        final List<Long> expected = List.of(진행중인_퀘스트_1.getId(), 진행중인_퀘스트_2.getId());

        // when
        final List<Long> actual = participantDao.findIdsByUserIdAndState(userId, DOING);

        // then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void 계속하는_퀘스트_ID들을_조회한다() {
        // given
        final Long userId = 1L;

        final Quest 계속하는_퀘스트_1 = 저장한다(QUEST_1.일반_퀘스트_생성());
        final Quest 계속하는_퀘스트_2 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 끝난_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 진행중인_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());

        퀘스트를_계속한다(저장한다(new Participant(userId, 계속하는_퀘스트_1)));
        퀘스트를_계속한다(저장한다(new Participant(userId, 계속하는_퀘스트_2)));
        퀘스트를_끝낸다(저장한다(new Participant(userId, 끝난_퀘스트)));
        저장한다(new Participant(userId, 진행중인_퀘스트));
        저장한다(QUEST_1.일반_퀘스트_생성());

        final List<Long> expected = List.of(계속하는_퀘스트_1.getId(), 계속하는_퀘스트_2.getId());

        // when
        final List<Long> actual = participantDao.findIdsByUserIdAndState(userId, CONTINUE);

        // then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void 끝난_퀘스트_ID들을_조회한다() {
        // given
        final Long userId = 1L;

        final Quest 끝난_퀘스트_1 = 저장한다(QUEST_1.일반_퀘스트_생성());
        final Quest 끝난_퀘스트_2 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 계속하는_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());
        final Quest 진행_중인_퀘스트 = 저장한다(QUEST_2.일반_퀘스트_생성());
        
        퀘스트를_끝낸다(저장한다(new Participant(userId, 끝난_퀘스트_1)));
        퀘스트를_끝낸다(저장한다(new Participant(userId, 끝난_퀘스트_2)));
        퀘스트를_계속한다(저장한다(new Participant(userId, 계속하는_퀘스트)));
        저장한다(new Participant(userId, 진행_중인_퀘스트));
        저장한다(QUEST_1.일반_퀘스트_생성());

        final List<Long> expected = List.of(끝난_퀘스트_1.getId(), 끝난_퀘스트_2.getId());

        // when
        final List<Long> actual = participantDao.findIdsByUserIdAndState(userId, FINISHED);

        // then
        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
