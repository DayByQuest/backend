package daybyquest.quest.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestLabelsRequest {

    private List<String> labels;
}
