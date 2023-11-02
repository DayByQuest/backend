package daybyquest.badge.domain;


import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class OwningId implements Serializable {

    private Long userId;

    private Long badge;

}
