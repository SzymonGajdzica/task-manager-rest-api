package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.models.CodeEntity;

public interface BaseCodeRepository<T extends CodeEntity> extends JpaRepository<T, String> {

    default T getById(String code) {
        return findById(code).orElseThrow(() -> new NotFoundException(code));
    }

}
