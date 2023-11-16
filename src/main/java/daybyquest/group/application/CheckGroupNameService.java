package daybyquest.group.application;

import daybyquest.group.domain.Groups;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckGroupNameService {

    private final Groups groups;

    public CheckGroupNameService(final Groups groups) {
        this.groups = groups;
    }

    @Transactional(readOnly = true)
    public void invoke(final String name) {
        groups.validateNotExistentByName(name);
    }
}
