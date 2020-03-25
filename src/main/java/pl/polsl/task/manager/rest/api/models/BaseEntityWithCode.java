package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode
@MappedSuperclass
@Data
@NoArgsConstructor
@ToString
public class BaseEntityWithCode {

    @Id
    @NonNull
    private String code;

}