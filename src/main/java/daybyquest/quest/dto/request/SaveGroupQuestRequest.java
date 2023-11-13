package daybyquest.quest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveGroupQuestRequest {

    private Long groupId;

    private String imageDescription;
}
