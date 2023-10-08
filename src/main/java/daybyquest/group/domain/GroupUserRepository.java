package daybyquest.group.domain;

import java.util.List;
import org.springframework.data.repository.Repository;

public interface GroupUserRepository extends Repository<GroupUser, GroupUserId> {

    GroupUser save(GroupUser groupUser);

    List<GroupUser> findAllByGroupId(Long groupId);

}
