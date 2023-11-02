package daybyquest.badge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveBadgeRequest {

    @NotBlank
    private String name;
}
