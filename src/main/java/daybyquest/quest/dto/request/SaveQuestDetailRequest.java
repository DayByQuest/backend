package daybyquest.quest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SaveQuestDetailRequest {

    @NotBlank
    private String title;

    private String content;

    @NotBlank
    private String interest;

    @NotBlank
    private String label;

    private Long rewardCount;
}
