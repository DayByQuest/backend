package daybyquest.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveUserRequest {

    @NotBlank
    String username;

    @NotBlank
    String email;

    @NotBlank
    String name;
}
