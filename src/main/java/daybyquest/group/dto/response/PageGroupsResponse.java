package daybyquest.group.dto.response;

import java.util.List;

public record PageGroupsResponse(List<GroupResponse> groups, Long lastId) {

}
