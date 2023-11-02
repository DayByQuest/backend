package daybyquest.group.domain;

import static daybyquest.group.domain.GroupUserRole.MANAGER;
import static daybyquest.group.domain.GroupUserRole.MEMBER;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@IdClass(GroupUserId.class)
@EntityListeners(AuditingEntityListener.class)
public class GroupUser {

    @Id
    private Long userId;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private GroupUserRole role;

    @CreatedDate
    private LocalDateTime registeredAt;

    public GroupUser(final Long userId, final Group group, final GroupUserRole role) {
        this.userId = userId;
        this.group = group;
        this.role = role;
    }

    public static GroupUser createGroupMember(final Long userId, final Group group) {
        return new GroupUser(userId, group, MEMBER);
    }

    public static GroupUser createGroupManager(final Long userId, final Group group) {
        return new GroupUser(userId, group, MANAGER);
    }

    public Long getGroupId() {
        return group.getId();
    }

    public boolean isManager() {
        return role == MANAGER;
    }
}
