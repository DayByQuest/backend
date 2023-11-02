package daybyquest.group.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

interface GroupUserRepository extends Repository<GroupUser, GroupUserId> {

    GroupUser save(final GroupUser groupUser);

    @Query("SELECT count (gu) > 0 FROM GroupUser gu WHERE gu.userId=:userId and gu.group.id = :groupId")
    boolean existsByUserIdAndGroupId(final Long userId, final Long groupId);
}
