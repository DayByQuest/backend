package daybyquest.quest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveGroupQuestRequest {

    @NotBlank
    private Long groupId;

    @NotBlank
    private String imageDescription;
}
