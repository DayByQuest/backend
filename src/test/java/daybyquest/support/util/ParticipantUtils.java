package daybyquest.support.util;

import daybyquest.participant.domain.Participant;
import daybyquest.participant.domain.ParticipantState;
import org.springframework.test.util.ReflectionTestUtils;

public class ParticipantUtils {

    public static void 게시물_연결_횟수를_지정한다(final Participant participant, final Long linkedCount) {
        ReflectionTestUtils.setField(participant, "linkedCount", linkedCount);
    }

    public static void 퀘스트를_끝낸다(final Participant participant) {
        ReflectionTestUtils.setField(participant, "state", ParticipantState.FINISHED);
    }

    public static void 퀘스트를_계속한다(final Participant participant) {
        ReflectionTestUtils.setField(participant, "state", ParticipantState.CONTINUE);
    }
}
