package daybyquest.quest.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
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

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime expiredAt;

    private String imageDescription;
}
