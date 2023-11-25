package daybyquest.user.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateUserInterestRequest {

    @NotNull
    @Size(max = 5)
    private Set<String> interests;
}
