package daybyquest.auth;

import daybyquest.auth.domain.AccessUser;
import daybyquest.global.error.exception.BadAuthorizationException;
import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorizationAspect {

    @Before("@annotation(authorization)")
    public void authorize(final JoinPoint joinPoint, final Authorization authorization) {
        final AccessUser accessUser = (AccessUser) Arrays.stream(joinPoint.getArgs())
                .filter(AccessUser.class::isInstance).findFirst()
                .orElseThrow(BadAuthorizationException::new);
        if (authorization.required() && accessUser.isGuest()) {
            throw new BadAuthorizationException();
        }
        if (authorization.admin() && !accessUser.isAdmin()) {
            throw new BadAuthorizationException();
        }
    }
}
