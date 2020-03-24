package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Table(name = "objects")
@Entity
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Object extends BaseEntityWithId {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @ManyToOne(optional = false)
    @NonNull
    private ObjectType objectType;

    @ManyToOne(optional = false)
    @NonNull
    private Client client;

    @OneToMany(mappedBy = "object")
    @NonNull
    private List<Request> requests = new LinkedList<>();
}
