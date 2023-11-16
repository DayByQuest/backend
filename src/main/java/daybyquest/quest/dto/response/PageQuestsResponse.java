package daybyquest.quest.dto.response;

import java.util.List;

public record PageQuestsResponse(List<QuestResponse> users, Long lastId) {

}
