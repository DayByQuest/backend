package daybyquest.user.dto.response;

import java.util.List;

public record PageProfilesResponse(List<ProfileResponse> users, Long lastId) {

}
