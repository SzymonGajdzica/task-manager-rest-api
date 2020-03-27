package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.models.Worker;

import java.util.List;

@Repository
public interface ActivityRepository extends BaseIdRepository<Activity> {

    List<Activity> findAllByWorker(Worker worker);

}
