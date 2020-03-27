package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Client;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.Request;

import java.util.List;

@Repository
public interface RequestRepository extends BaseIdRepository<Request> {

    List<Request> findAllByManager(Manager manager);

    List<Request> findAllByObject_Client(Client client);

}
