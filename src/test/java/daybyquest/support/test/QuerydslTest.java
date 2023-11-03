package daybyquest.support.test;

import daybyquest.global.config.JpaConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({JpaConfig.class})
public abstract class QuerydslTest {

    @PersistenceContext
    private EntityManager entityManager;

    protected <T> T 저장한다(final T t) {
        entityManager.persist(t);
        return t;
    }
}
