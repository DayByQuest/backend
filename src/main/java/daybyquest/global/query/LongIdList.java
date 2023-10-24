package daybyquest.global.query;

import java.util.List;
import lombok.Getter;

@Getter
public class LongIdList {

    private final List<Long> ids;

    public LongIdList(final List<Long> ids) {
        this.ids = ids;
    }

    public Long getLastId() {
        if (ids.isEmpty()) {
            return Long.MAX_VALUE;
        }
        return ids.get(ids.size() - 1);
    }
}
