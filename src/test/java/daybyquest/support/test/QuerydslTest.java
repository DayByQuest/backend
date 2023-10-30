package daybyquest.support.test;

import daybyquest.global.config.JpaConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({JpaConfig.class})
public abstract class QuerydslTest {

}
