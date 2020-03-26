package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Activity;

import java.util.List;

@Repository
public interface ActivityRepository extends BaseIdRepository<Activity> {

    List<Activity> findAllByWorkerId(Long workerId);

}
