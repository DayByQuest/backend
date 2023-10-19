package daybyquest.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserVisibilityRequest {

    @NotBlank
    String visibility;
}
