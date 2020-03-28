package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.models.*;

import java.util.List;

@Repository
public interface RequestRepository extends BaseIdRepository<Request> {

    List<Request> findAllByObjectClient(Client client);

    List<Request> findAllByActivitiesContains(List<Activity> activities);

    List<Request> findAllByActivitiesContainsAndObject(List<Activity> activities, Object object);

    List<Request> findAllByManagerAndObject(Manager manager, Object object);

}
