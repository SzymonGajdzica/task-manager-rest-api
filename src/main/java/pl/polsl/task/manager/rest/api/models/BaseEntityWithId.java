package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode
@MappedSuperclass
@Data
@NoArgsConstructor
@ToString
public class BaseEntityWithId {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NonNull
    protected Long id;

}
