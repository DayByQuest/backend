package daybyquest.quest.query;

import static daybyquest.participant.domain.ParticipantState.CONTINUE;
import static daybyquest.participant.domain.ParticipantState.DOING;
import static daybyquest.participant.domain.ParticipantState.FINISHED;
import static daybyquest.participant.domain.ParticipantState.NOT;
import static daybyquest.quest.domain.QuestCategory.GROUP;
import static daybyquest.quest.domain.QuestCategory.NORMAL;
import static daybyquest.support.fixture.BadgeFixtures.BADGE_1;
import static daybyquest.support.fixture.GroupFixtures.GROUP_1;
import static daybyquest.support.fixture.PostFixtures.POST_1;
import static daybyquest.support.fixture.PostFixtures.POST_2;
import static daybyquest.support.fixture.PostFixtures.POST_3;
import static daybyquest.support.fixture.QuestFixtures.QUEST_1;
import static daybyquest.support.fixture.UserFixtures.ALICE;
import static daybyquest.support.fixture.UserFixtures.BOB;
import static daybyquest.support.fixture.UserFixtures.CHARLIE;
import static daybyquest.support.fixture.UserFixtures.DAVID;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_계속한다;
import static daybyquest.support.util.ParticipantUtils.퀘스트를_끝낸다;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import daybyquest.badge.domain.Badge;
import daybyquest.global.error.exception.NotExistQuestException;
import daybyquest.group.domain.Group;
import daybyquest.participant.domain.Participant;
import daybyquest.quest.domain.Quest;
import daybyquest.support.test.QuerydslTest;
import daybyquest.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@Import(QuestDaoQuerydslImpl.class)
public class QuestDaoQuerydslImplTest extends QuerydslTest {

    @Autowired
    private QuestDaoQuerydslImpl questDao;

    @Test
    void 일반_퀘스트_ID로_데이터를_조회한다() {
        // given
        final Long userId = 1L;
        final Quest quest = 저장한다(QUEST_1.일반_퀘스트_생성());

        // when
        final QuestData questData = questDao.getById(userId, quest.getId());

        // then
        assertAll(() -> {
            assertThat(questData.getTitle()).isEqualTo(QUEST_1.title);
            assertThat(questData.getContent()).isEqualTo(QUEST_1.content);
            assertThat(questData.getInterestName()).isEqualTo(QUEST_1.interest);
            assertThat(questData.getCategory()).isEqualTo(NORMAL);
        });
    }

    @Test
    void 일반_퀘스트_ID로_데이터_조회_시_보상이_있다면_뱃지_정보를_포함한다() {
        // given
        final Long userId = 1L;
        final Badge badge = 저장한다(BADGE_1.생성());
        final Quest quest = 저장한다(QUEST_1.일반_퀘스트_생성(badge));

        // when
        final QuestData questData = questDao.getById(userId, quest.getId());

        // then
        assertAll(() -> {
            assertThat(questData.getRewardCount()).isEqualTo(QUEST_1.rewardCount);
            assertThat(questData.getBadgeId()).isEqualTo(badge.getId());
            assertThat(questData.getImage()).isEqualTo(badge.getImage());
        });
    }

    @Test
    void 그룹_퀘스트_ID로_데이터를_조회한다() {
        // given
        final Long userId = 1L;
        final Long groupId = 2L;
        final Quest quest = 저장한다(QUEST_1.그룹_퀘스트_생성(groupId));

        // when
        final QuestData questData = questDao.getById(userId, quest.getId());

        // then
        assertAll(() -> {
            assertThat(questData.getTitle()).isEqualTo(QUEST_1.title);
            assertThat(questData.getContent()).isEqualTo(QUEST_1.content);
            assertThat(questData.getInterestName()).isEqualTo(QUEST_1.interest);
            assertThat(questData.getCategory()).isEqualTo(GROUP);
            assertThat(questData.getGroupId()).isEqualTo(groupId);
        });
    }

    @Test
    void 그룹_퀘스트_ID로_데이터_조회_시_그룹_정보를_포함한다() {
        // given
        final Long userId = 1L;
        final Group group = 저장한다(GROUP_1.생성());
        final Quest quest = 저장한다(QUEST_1.그룹_퀘스트_생성(group));

        // when
        final QuestData questData = questDao.getById(userId, quest.getId());

        // then
        assertAll(() -> {
            assertThat(questData.getGroupId()).isEqualTo(group.getId());
            assertThat(questData.getImage()).isEqualTo(group.getImage());
        });
    }

    @Test
    void 데이터_조회_시_링크_성공한_게시물_수를_포함한다() {
        // given
        final User user = 저장한다(ALICE.생성());
        final Quest quest = 저장한다(QUEST_1.일반_퀘스트_생성());
        저장한다(POST_1.링크_성공_상태로_생성(user, quest));
        저장한다(POST_2.링크_성공_상태로_생성(user, quest));
        저장한다(POST_3.생성(user, quest));

        // when
        final QuestData questData = questDao.getById(user.getId(), quest.getId());

        // then
        assertThat(questData.getCurrentCount()).isEqualTo(2);
    }

    @Test
    void 데이터_조회_시_참여_정보를_포함한다() {
        // given
        final User alice = 저장한다(ALICE.생성());
        final User bob = 저장한다(BOB.생성());
        final User charlie = 저장한다(CHARLIE.생성());
        final User david = 저장한다(DAVID.생성());
        final Quest quest = 저장한다(QUEST_1.일반_퀘스트_생성());

        저장한다(new Participant(alice.getId(), quest));
        저장한다(퀘스트를_끝낸다(new Participant(bob.getId(), quest)));
        저장한다(퀘스트를_계속한다(new Participant(charlie.getId(), quest)));

        // when
        final QuestData aliceData = questDao.getById(alice.getId(), quest.getId());
        final QuestData bobData = questDao.getById(bob.getId(), quest.getId());
        final QuestData charlieData = questDao.getById(charlie.getId(), quest.getId());
        final QuestData davidData = questDao.getById(david.getId(), quest.getId());

        // then
        assertAll(() -> {
            assertThat(aliceData.getState()).isEqualTo(DOING);
            assertThat(bobData.getState()).isEqualTo(FINISHED);
            assertThat(charlieData.getState()).isEqualTo(CONTINUE);
            assertThat(davidData.getState()).isEqualTo(NOT);
        });
    }

    @Test
    void ID에_해당하는_퀘스트가_없으면_예외를_던진다() {
        // given & when & then
        assertThatThrownBy(() -> questDao.getById(1L, 2L))
                .isInstanceOf(NotExistQuestException.class);
    }
}
