package daybyquest.owner.domain;


import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwningId implements Serializable {

    private Long badge;

    private Long userId;

}
