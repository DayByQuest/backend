package daybyquest.auth;

import static daybyquest.global.error.ExceptionCode.INVALID_REQUEST;

import daybyquest.global.error.exception.BadRequestException;
import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String AUTHORIZATION_HEADER = "Authorization";

    private final static String AUTHORIZATION_HEADER_PREFIX = "UserId ";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) &&
            parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String header = webRequest.getHeader(AUTHORIZATION_HEADER);
        if (header == null || !header.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
            throw new BadRequestException(INVALID_REQUEST);
        }
        final String userId = header.substring(AUTHORIZATION_HEADER_PREFIX.length());
        return Long.valueOf(userId);
    }

}