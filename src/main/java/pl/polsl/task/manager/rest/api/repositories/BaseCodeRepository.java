package pl.polsl.task.manager.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.models.BaseEntityWithCode;

public interface BaseCodeRepository<T extends BaseEntityWithCode> extends JpaRepository<T, String> {

    default T getById(String code){
        return findById(code).orElseThrow(() -> new NotFoundException(code));
    }

}
