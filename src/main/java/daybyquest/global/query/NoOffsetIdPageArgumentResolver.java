package daybyquest.global.query;

import daybyquest.global.error.exception.BadRequestException;
import jakarta.annotation.Nonnull;
import java.util.regex.Pattern;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class NoOffsetIdPageArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int MAX_LIMIT = 15;
    private static final Pattern NUMBER = Pattern.compile("^[0-9]*$");


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NoOffsetIdPage.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String lastIdArgument = webRequest.getParameter("lastId");
        final String limitArgument = webRequest.getParameter("limit");
        return new NoOffsetIdPage(convertAndValidateLastId(lastIdArgument),
                convertAndValidateLimit(limitArgument));
    }

    private Long convertAndValidateLastId(String lastId) {
        if (lastId == null) {
            return 0L;
        }
        if (!NUMBER.matcher(lastId).matches()) {
            throw new BadRequestException();
        }
        return Long.parseLong(lastId);
    }

    private int convertAndValidateLimit(String limit) {
        if (limit == null || !NUMBER.matcher(limit).matches()) {
            throw new BadRequestException();
        }
        final int parsedLimit = Integer.parseInt(limit);
        if (parsedLimit > MAX_LIMIT) {
            throw new BadRequestException();
        }
        return parsedLimit;
    }
}
