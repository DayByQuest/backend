package daybyquest.quest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveQuestRequest {

    private Long badgeId;

    private String imageDescription;
}
