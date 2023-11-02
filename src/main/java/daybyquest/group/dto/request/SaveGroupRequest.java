package daybyquest.group.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveGroupRequest {

    @NotBlank
    private String name;

    private String description;

    @NotBlank
    private String interest;
}
