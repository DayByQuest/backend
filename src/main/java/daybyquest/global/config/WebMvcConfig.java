package daybyquest.global.config;

import daybyquest.auth.AccessUserArgumentResolver;
import daybyquest.global.query.NoOffsetIdPageArgumentResolver;
import daybyquest.global.query.NoOffsetTimePageArgumentResolver;
import daybyquest.user.domain.Users;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableAspectJAutoProxy
public class WebMvcConfig implements WebMvcConfigurer {

    private final Users users;

    public WebMvcConfig(final Users users) {
        this.users = users;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AccessUserArgumentResolver(users));
        resolvers.add(new NoOffsetIdPageArgumentResolver());
        resolvers.add(new NoOffsetTimePageArgumentResolver());
    }
}
