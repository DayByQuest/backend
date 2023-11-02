package daybyquest.global.query;

import daybyquest.global.error.exception.BadRequestException;
import jakarta.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class NoOffsetTimePageArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int MAX_LIMIT = 15;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm");

    private static final Pattern NUMBER = Pattern.compile("^[0-9]*$");

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(NoOffsetTimePage.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String lastTimeArgument = webRequest.getParameter("lastTime");
        final String limitArgument = webRequest.getParameter("limit");
        return new NoOffsetTimePage(convertAndValidateLastTime(lastTimeArgument),
                convertAndValidateLimit(limitArgument));
    }

    private LocalDateTime convertAndValidateLastTime(String lastTime) {
        if (lastTime == null) {
            return null;
        }
        return LocalDateTime.parse(lastTime, DATE_TIME_FORMATTER);
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
