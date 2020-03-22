package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {

}
