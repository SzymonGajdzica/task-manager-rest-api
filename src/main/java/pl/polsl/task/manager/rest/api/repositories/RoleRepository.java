package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.task.manager.rest.api.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
