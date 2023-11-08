package daybyquest.participant.presentation;

import static daybyquest.global.error.ExceptionCode.INVALID_REQUEST;

import daybyquest.global.error.exception.BadRequestException;
import daybyquest.participant.domain.ParticipantState;
import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class ParticipantStateArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String STATE_PARAMETER = "state";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(ParticipantState.class);
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
            @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        final String stateArgument = webRequest.getParameter(STATE_PARAMETER);
        try {
            return ParticipantState.valueOf(stateArgument);
        } catch (final IllegalArgumentException e) {
            throw new BadRequestException(INVALID_REQUEST, List.of(STATE_PARAMETER));
        }
    }
}
