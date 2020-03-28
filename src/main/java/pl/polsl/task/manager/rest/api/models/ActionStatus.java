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
import java.util.LinkedList;
import java.util.List;

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
    private List<ActionStatus> childActionStatuses = new LinkedList<>();

    @ManyToMany
    @NonNull
    private List<ActionStatus> parentActionStatuses = new LinkedList<>();

}