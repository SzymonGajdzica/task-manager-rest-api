package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Activity;

@Repository
public interface ActivityRepository extends BaseIdRepository<Activity> {

}
