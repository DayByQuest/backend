package daybyquest.quest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveQuestRequest {

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private String interest;

    private Long badgeId;

    @NotBlank
    private Long rewardCount;

    private String imageDescription;
}
