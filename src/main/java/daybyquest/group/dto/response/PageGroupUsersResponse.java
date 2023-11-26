package daybyquest.group.dto.response;

import java.util.List;

public record PageGroupUsersResponse(List<GroupUserResponse> users, Long lastId) {

}
