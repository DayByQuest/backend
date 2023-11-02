package daybyquest.interest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SaveInterestRequest {

    @NotBlank
    private String name;
}
