package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Client;
import pl.polsl.task.manager.rest.api.models.Object;

import java.util.List;

@Repository
public interface ObjectRepository extends BaseIdRepository<Object> {

    List<Object> getAllByClient(Client client);

}
