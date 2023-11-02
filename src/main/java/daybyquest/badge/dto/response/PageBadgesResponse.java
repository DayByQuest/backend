package daybyquest.badge.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PageBadgesResponse {

    private List<BadgeResponse> badges;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastTime;

    public PageBadgesResponse(final List<BadgeResponse> badges, final LocalDateTime lastTime) {
        this.badges = badges;
        this.lastTime = lastTime;
    }
}
