package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.models.IdEntity;

public interface BaseIdRepository<T extends IdEntity> extends JpaRepository<T, Long> {

    default T getById(Long id) {
        return findById(id).orElseThrow(() -> new NotFoundException(id));
    }

}
