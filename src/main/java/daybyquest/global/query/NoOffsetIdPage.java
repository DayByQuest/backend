package daybyquest.global.query;

import lombok.Getter;

@Getter
public class NoOffsetIdPage {

    private final Long lastId;

    private final int limit;

    public NoOffsetIdPage(final Long lastId, final int limit) {
        this.lastId = lastId;
        this.limit = limit;
    }
}
