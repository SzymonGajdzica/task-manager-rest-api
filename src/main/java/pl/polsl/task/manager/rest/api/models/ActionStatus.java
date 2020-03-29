package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "action_statuses")
public class ActionStatus extends CodeEntity {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @ManyToMany(mappedBy = "parentActionStatuses")
    @NonNull
    private Set<ActionStatus> childActionStatuses = new HashSet<>();

    @ManyToMany
    @NonNull
    private Set<ActionStatus> parentActionStatuses = new HashSet<>();

}