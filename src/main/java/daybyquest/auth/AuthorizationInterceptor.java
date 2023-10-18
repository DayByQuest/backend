package daybyquest.auth;

import daybyquest.global.error.ExceptionCode;
import daybyquest.global.error.exception.BadRequestException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private final static String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean preHandle(@Nonnull final HttpServletRequest request,
        @Nonnull final HttpServletResponse response, @Nonnull final Object handler)
        throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        final Authorization authorization = getAuthorizationAnnotation(handler);
        validateAuthorization(authorization, request);
        return true;
    }

    private Authorization getAuthorizationAnnotation(final Object handler) {
        final HandlerMethod handlerMethod = (HandlerMethod) handler;
        return handlerMethod.getMethodAnnotation(Authorization.class);
    }

    private void validateAuthorization(final Authorization authorization, final HttpServletRequest request) {
        if (authorization == null) {
            return;
        }
        final String header = request.getHeader(AUTHORIZATION_HEADER);
        validateHeader(authorization, header);
    }

    private void validateHeader(final Authorization authorization, final String header) {
        if (header == null && authorization.required()) {
            throw new BadRequestException(ExceptionCode.BAD_AUTHORIZATION);
        }
        if (AuthorizationHeaderExtractor.extract(header) == null && authorization.required()) {
            throw new BadRequestException(ExceptionCode.BAD_AUTHORIZATION);
        }
    }

}
