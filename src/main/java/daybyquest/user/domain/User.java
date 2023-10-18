package daybyquest.user.domain;

import static jakarta.persistence.GenerationType.IDENTITY;

import daybyquest.global.error.exception.InvalidDomainException;
import daybyquest.global.vo.Image;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Embedded
    private Image image;

    public User(String username, String email, String name, Image image) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.image = image;
        this.state = UserState.USER;
        validate();
    }

    private void validate() {
        validateUsername();
        validateName();
        validateEmail();
    }

    private void validateUsername() {
        if(this.username.length() > MAX_USERNAME_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateName() {
        if(this.name.length() > MAX_NAME_LENGTH) {
            throw new InvalidDomainException();
        }
    }

    private void validateEmail() {
        if(this.email.length() > MAX_EMAIL_LENGTH || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDomainException();
        }
    }
}
