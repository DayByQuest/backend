package daybyquest.auth;

import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final static String AUTHORIZATION_HEADER = "Authorization";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserId.class) &&
            parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
        @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        final String header = webRequest.getHeader(AUTHORIZATION_HEADER);
        final String userId = AuthorizationHeaderExtractor.extract(header);
        if (userId == null) {
            return -1L;
        }
        return Long.valueOf(userId);
    }

}