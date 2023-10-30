package daybyquest.owner.domain;


import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OwningId implements Serializable {

    private Long badge;

    private Long userId;

}
