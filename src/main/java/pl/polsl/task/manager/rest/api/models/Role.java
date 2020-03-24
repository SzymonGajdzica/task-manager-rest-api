package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.LinkedList;
import java.util.List;

@Table(name = "roles")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Role extends BaseEntityWithCode {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @OneToMany(mappedBy = "role")
    @NonNull
    private List<User> users = new LinkedList<>();

}
