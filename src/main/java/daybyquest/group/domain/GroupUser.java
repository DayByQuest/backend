package daybyquest.group.domain;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@IdClass(GroupUserId.class)
public class GroupUser {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @Id
    private Long userId;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private GroupUserRole role;

    public GroupUser(Group group, Long userId) {
        this.group = group;
        this.userId = userId;
        this.role = GroupUserRole.MEMBER;
    }

}
