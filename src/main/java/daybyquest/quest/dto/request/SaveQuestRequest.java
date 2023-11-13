package daybyquest.quest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveQuestRequest {

    @NotBlank
    private Long badgeId;

    @NotBlank
    private String imageDescription;
}
