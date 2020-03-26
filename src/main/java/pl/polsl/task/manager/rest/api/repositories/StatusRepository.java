package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.ActionStatus;

@Repository
public interface StatusRepository extends BaseCodeRepository<ActionStatus> {

}
