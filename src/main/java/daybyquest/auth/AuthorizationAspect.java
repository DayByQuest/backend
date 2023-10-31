package daybyquest.auth;

import daybyquest.auth.domain.AccessUser;
import daybyquest.global.error.exception.BadAuthorizationException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AuthorizationAspect {

    @Around("@annotation(authorization)")
    public Object authorize(final ProceedingJoinPoint joinPoint, final Authorization authorization)
            throws Throwable {
        final AccessUser accessUser = (AccessUser) Arrays.stream(joinPoint.getArgs())
                .filter(AccessUser.class::isInstance).findFirst()
                .orElseThrow(BadAuthorizationException::new);
        if (authorization.required() && accessUser.isGuest()) {
            throw new BadAuthorizationException();
        }
        if (authorization.admin() && !accessUser.isAdmin()) {
            throw new BadAuthorizationException();
        }
        return joinPoint.proceed();
    }
}
