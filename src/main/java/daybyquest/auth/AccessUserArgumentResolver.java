package daybyquest.auth;

import daybyquest.auth.domain.AccessUser;
import daybyquest.global.error.exception.BadAuthorizationException;
import daybyquest.global.error.exception.NotExistUserException;
import daybyquest.user.domain.User;
import daybyquest.user.domain.Users;
import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AccessUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String AUTHORIZATION_HEADER = "Authorization";

    private final Users users;

    public AccessUserArgumentResolver(final Users users) {
        this.users = users;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(AccessUser.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String header = webRequest.getHeader(AUTHORIZATION_HEADER);
        final String headerValue = AuthorizationHeaderExtractor.extract(header);
        if (headerValue == null) {
            return AccessUser.ofGuest();
        }
        return convertUserIdToLoginUser(Long.parseLong(headerValue));
    }

    private AccessUser convertUserIdToLoginUser(final Long userId) {
        User user;
        try {
            user = users.getById(userId);
        } catch (NotExistUserException e) {
            throw new BadAuthorizationException();
        }
        if (user.isAdmin()) {
            return AccessUser.ofAdmin(userId);
        }
        return AccessUser.ofUser(userId);
    }
}