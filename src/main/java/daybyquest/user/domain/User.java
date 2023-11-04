package daybyquest.user.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.image.domain.Image;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private static final int MAX_USERNAME_LENGTH = 15;

    private static final int MAX_NAME_LENGTH = 20;

    private static final int MAX_EMAIL_LENGTH = 30;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-z0-9._-]+@[a-z]+[.]+[a-z]{2,3}$");

    private static final int MAX_INTEREST_SIZE = 5;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = MAX_USERNAME_LENGTH)
    private String username;

    @Column(nullable = false, unique = true, length = MAX_EMAIL_LENGTH)
    private String email;

    @Column(nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserState state;

    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private UserVisibility visibility;

    @Embedded
    private Image image;

    @ElementCollection
    @CollectionTable(name = "user_interest", joinColumns = @JoinColumn(name = "user_id"))
    @Column(length = 10)
    private List<String> interests;

    public User(String username, String email, String name, Image image) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.image = image;
        this.state = UserState.USER;
        this.visibility = UserVisibility.PUBLIC;
        this.interests = new ArrayList<>();
        validate();
    }

    public String getImageIdentifier() {
        return image.getImageIdentifier();
    }

    private void validate() {
        validateUsername();
        validateName();
        validateEmail();
    }

    private void validateUsername() {
        if (username.isEmpty() || username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateName() {
        if (name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateEmail() {
        if (email.isEmpty() || email.length() > MAX_EMAIL_LENGTH || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDomainException();
        }
    }

    public void updateUsername(final String username) {
        validateUpdatable();
        this.username = username;
        validateUsername();
    }

    private void validateUpdatable() {
        if (!isUser()) {
            throw new InvalidDomainException();
        }
    }

    public void updateName(final String name) {
        validateUpdatable();
        this.name = name;
        validateName();
    }

    public void updateVisibility(final UserVisibility visibility) {
        validateUpdatable();
        this.visibility = visibility;
    }

    public void updateInterests(final Collection<String> interests) {
        validateUpdatable();
        this.interests.clear();
        this.interests.addAll(interests);
        validateInterests();
    }

    private void validateInterests() {
        if (this.interests.size() > MAX_INTEREST_SIZE) {
            throw new InvalidDomainException();
        }
    }

    public void updateImage(Image image) {
        validateUpdatable();
        this.image = image;
    }

    public boolean isUser() {
        return state == UserState.USER || state == UserState.MODERATOR;
    }

    public boolean isAdmin() {
        return state == UserState.ADMIN;
    }

    public boolean isModerator() {
        return state == UserState.MODERATOR;
    }
}
