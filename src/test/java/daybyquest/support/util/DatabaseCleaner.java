package daybyquest.support.util;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;

    @PostConstruct
    public void setUp() {
        this.tableNames = entityManager.getMetamodel().getEntities().stream()
                .filter(e -> e.getJavaType().getDeclaredAnnotation(Entity.class) != null)
                .map(e -> convertToSnakeCase(e.getName()))
                .collect(Collectors.toList());
        this.tableNames.add("post_image");
    }

    private String convertToSnakeCase(final String original) {
        return original.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }

    @Transactional
    public void clean() {
        entityManager.flush();
        entityManager.createNativeQuery("SET foreign_key_checks = 0").executeUpdate();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE `" + tableName + "`").executeUpdate();
        }
        entityManager.createNativeQuery("SET foreign_key_checks = 1").executeUpdate();
    }
}
